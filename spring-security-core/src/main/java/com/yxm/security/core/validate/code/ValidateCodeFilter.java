package com.yxm.security.core.validate.code;
import com.yxm.security.core.properties.SecurityConstants;
import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.validate.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证码过滤器
 * @author yexinming
 * @date 2020/2/24
 **/
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    //此处不用注解@Autowire 而是使用setter方法将在WebSecurityConfig设置
    /**
     *系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();
    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        //我们获取配置的ImageCodeProperties里面的url,转化为数据,添加到urls里面去
       /* String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(), ",");
        if(configUrls!=null && configUrls.length>0){
            for (String configUrl:configUrls) {
                urls.add(configUrl);
            }
        }
        //"/authentication/form"一定会校验验证码的
        urls.add("/authentication/form");*/

       //IMAGE
       urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.IMAGE);
        //SMS
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     * @param urlString
     * @param type
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
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
    /*    boolean action = false;
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
        filterChain.doFilter(request,response);*/
        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
