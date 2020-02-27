package com.yxm.security.core.validate.code;
import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.validate.ImageCode;
import com.yxm.security.core.validate.code.processor.ValidateCodeProcessor;
import com.yxm.security.core.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证码过滤器
 * @author yexinming
 * @date 2020/2/24
 **/
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Logger logger = LoggerFactory.getLogger(getClass());
    //需要校验的url都在这里面添加
    private Set<String> urls = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    //此处不用注解@Autowire 而是使用setter方法将在WebSecurityConfig设置
    private SecurityProperties securityProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        //我们获取配置的ImageCodeProperties里面的url,转化为数据,添加到urls里面去
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        if(configUrls!=null && configUrls.length>0){
            for (String configUrl:configUrls) {
                urls.add(configUrl);
            }
        }
        //"/authentication/form"一定会校验验证码的
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("验证码过滤器:doFilterInternal: requestURI:[{}]  requestMethod:[{}]",request.getRequestURI(),request.getMethod());
        /**
         * 如果是需要认证请求,我们进行家宴
         * 如果校验失败,使用我们自定义的校验失败处理类处理
         * 如果不需要认证,我们放行进入下一个Filter
         */

        //在afterPropertiesSet执行之后,url初始化完毕之后,但是此时我们判断不能用StringUtils.equals,我们我们urls里面有 url: /user,/user/* 带星号的配置
        // 用户请求有可能是/user/1、/user/2  我们需要使用Spring的 AntPathMatcher
        boolean action = false;
        for (String url:urls) {
            //如果配置的url和请求的url相同时候,需要校验
           if(antPathMatcher.match(url,request.getRequestURI())){
               action = true;
           }
        }
        if(action){
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
       // ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX);
       // String s = StringUtils.substringAfter(servletWebRequest.getRequest().getRequestURI(), "/code/");
        ImageCode codeInSession = (ImageCode)sessionStrategy.getAttribute(servletWebRequest, ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
           //2.获取请求中的验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
          if(StringUtils.isBlank(codeInRequest)){
              throw new ValidateCodeException("验证码的值不能为空");
          }

          if(codeInSession == null){
              throw new ValidateCodeException("验证码不存在");
          }

          if(codeInSession.isExpried()){
              sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeProcessor.SESSION_KEY_PREFIX);
              throw new ValidateCodeException("验证码已过期");
          }

          if(!StringUtils.equals(codeInSession.getCode(),codeInRequest)){
              throw new ValidateCodeException("验证码不匹配");
          }

          sessionStrategy.removeAttribute(servletWebRequest,ValidateCodeProcessor.SESSION_KEY_PREFIX+"IMAGE");
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
