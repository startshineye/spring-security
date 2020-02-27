
package com.yxm.security.core.validate.code.processor;
import com.yxm.security.core.validate.ValidateCode;
import com.yxm.security.core.validate.code.ValidateCodeGenerator;
import com.yxm.security.core.validate.code.ValidateCodeProcessor;
import com.yxm.security.core.validate.code.ValidateCodeType;
import com.yxm.security.core.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import java.util.Map;
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	@Override
	public void create(ServletWebRequest request) throws Exception {
		//主干逻辑3步骤:1.生成  2.保存 3.发送(发送是抽象方法,让子类自己去实现)
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码:这里涉及到Spring里面的开发技巧;依赖查找
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
		/*String type = getProcessorType(request);*/
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if(validateCodeGenerator == null){
           throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}
	/**
	 * 保存校验码
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}
	/**
	 * 构建验证码放入session时的key
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	/**
	 * 发送校验码，由子类实现
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	/**
	 * 根据请求的url获取校验码类型:根据请求后半段不同对应的校验器也不同
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(ServletWebRequest request){
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType processorType = getValidateCodeType(request);
		String sessionKey = getSessionKey(request);

		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, sessionKey);
	}
}
