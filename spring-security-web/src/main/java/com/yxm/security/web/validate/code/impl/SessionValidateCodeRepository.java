package com.yxm.security.web.validate.code.impl;

import com.yxm.security.core.validate.ValidateCode;
import com.yxm.security.core.validate.code.ValidateCodeRepository;
import com.yxm.security.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
/**
 * @author yexinming
 * @date 2020/3/5
 **/
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request,getSessionKey(request, validateCodeType),code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode)sessionStrategy.getAttribute(request,getSessionKey(request,validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request,getSessionKey(request,validateCodeType));
    }

    /**
     * 构建验证码放入session时的key
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }
}
