package com.yxm.security.app;

import com.yxm.security.app.authentication.MyAuthenticationFailureHandler;
import com.yxm.security.app.authentication.MyAuthenticationSuccessHandler;
import com.yxm.security.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.yxm.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.yxm.security.core.properties.SecurityConstants;
import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 作为资源服务器时候,别人访问我的资源时候,我的安全配置是怎样的。
 * @author yexinming
 * @date 2020/3/3
 **/
@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig; //短信验证码授权配置
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;//验证码过滤器配置

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);

        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(mySocialSecurityConfig)//配置第三方social
                .and()
                .authorizeRequests()
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getSignUpUrl())
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                .csrf().disable();
    }
}
