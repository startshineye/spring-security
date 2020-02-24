package com.yxm.security.core.validate.code;
import com.yxm.security.core.validate.ImageCode;
import com.yxm.security.core.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 验证码过滤器
 * @author yexinming
 * @date 2020/2/24
 **/
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("验证码过滤器:doFilterInternal: requestURI:[{}]  requestMethod:[{}]",request.getRequestURI(),request.getMethod());
        /**
         * 如果是需要认证请求,我们进行家宴
         * 如果校验失败,使用我们自定义的校验失败处理类处理
         * 如果不需要认证,我们放行进入下一个Filter
         */
        if(StringUtils.equals("/authentication/form",request.getRequestURI()) && StringUtils.endsWithIgnoreCase(request.getMethod(),"post")){
           try{
               validate(new ServletWebRequest(request));
           }catch (ValidateCodeException e){
               authenticationFailureHandler.onAuthenticationFailure(request,response,e);
               //抛出异常校验失败,不再走小面过滤器执行链
               return;
           }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
           //1.获取存放到session中的验证码
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
           //2.获取请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
          if(StringUtils.isBlank(codeInRequest)){
              throw new ValidateCodeException("验证码的值不能为空");
          }

          if(codeInSession == null){
              throw new ValidateCodeException("验证码不存在");
          }

          if(codeInSession.isExpried()){
              sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeController.SESSION_KEY);
              throw new ValidateCodeException("验证码已过期");
          }

          if(StringUtils.equals(codeInSession.getCode(),codeInRequest)){
              throw new ValidateCodeException("验证码不匹配");
          }

          sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeController.SESSION_KEY);
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
