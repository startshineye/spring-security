package com.yxm.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author yexinming
 * @date 2020/2/26
 **/
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 在我们认证过程中是不需要密码,认证信息是手机号
     */
    public static final String YXM_FORM_MOBILE_KEY = "mobile";
    /**
     * 请求中携带参数的名字是什么?
     */
    private String mobileParameter = "mobile";
    /**
     * 此过滤器只处理post请求
     */
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        //指明当前过滤器处理的请求是什么?
        super(new AntPathRequestMatcher("/authentication/moblie", "POST"));
    }
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();
            //实例化未认证的token
            SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
            this.setDetails(request, authRequest);
            /**
             * AuthenticationManager进行调用
             */
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
    public final String getMobileParameter() {
        return this.mobileParameter;
    }
}
