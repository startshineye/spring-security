package com.yxm.security.web.config;

import com.yxm.security.core.properties.BrowserProperties;
import com.yxm.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yexinming
 * @date 2020/2/22
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 定义web安全配置类:覆盖config方法
     * 1.参数为HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        /**
         * 定义了任何请求都需要表单认证
         */
       http.formLogin()//表单登录---指定了身份认证方式
          // .loginPage("/login.html")
               .loginPage("/authentication/require")
           .loginProcessingUrl("/authentication/form")//配置UsernamePasswordAuthenticationFilter需要拦截的请求
       // http.httpBasic()//http的basic登录
          .and()
          .authorizeRequests()//对请求进行授权
          .antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()).permitAll()//对匹配login.html的请求允许访问
          .anyRequest()//任何请求
          .authenticated()
           .and()
           .csrf().disable();//都需要认证
    }
}
