package com.yxm.security.core.validate.code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
/**
 * @author yexinming
 * @date 2020/2/27
 **/
@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private Filter validateCodeFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
       // super.configure(builder);
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
