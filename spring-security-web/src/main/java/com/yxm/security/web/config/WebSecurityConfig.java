package com.yxm.security.web.config;
import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.validate.code.ValidateCodeFilter;
import com.yxm.security.web.authentication.MyAuthenticationFailureHandler;
import com.yxm.security.web.authentication.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author yexinming
 * @date 2020/2/22
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //
        //因为是Jdbc操作,所以我们需要注入数据源:org.springframework.jdbc.core.support.JdbcDaoSupport
        //tokenRepository继承org.springframework.jdbc.core.support.JdbcDaoSupport
        tokenRepository.setDataSource(dataSource);
        System.out.println("PersistentTokenRepository--dataSource:>dataSource");
        //tokenRepository.setCreateTableOnStartup(true);//系统启动的时候创建:CREATE_TABLE_SQL表
        return tokenRepository;

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
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);//传递securityProperties
        validateCodeFilter.afterPropertiesSet();

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//自定义的额过滤器加到UsernamePasswordAuthenticationFilter前面去
               .formLogin()//表单登录---指定了身份认证方式
                      // .loginPage("/login.html")
                       .loginPage("/authentication/require")
                       .loginProcessingUrl("/authentication/form")//配置UsernamePasswordAuthenticationFilter需要拦截的请求
                       .successHandler(myAuthenticationSuccessHandler)//表单登录成功之后用自带的处理器
                       .failureHandler(myAuthenticationFailureHandler)//表单登录失败之后用自带的处理器
                   // http.httpBasic()//http的basic登录
                      .and()
                .rememberMe()
                      .tokenRepository(persistentTokenRepository())//配置remeberMe的token操作
                      .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//配置token失效秒数
                      .userDetailsService(userDetailsService)//配置操作数据库用户的service
                      .and()
          .authorizeRequests()//对请求进行授权
                      .antMatchers("/authentication/require",
                              securityProperties.getBrowser().getLoginPage(),
                              "/code/*").permitAll()//对匹配login.html的请求允许访问
                      .anyRequest()//任何请求
                      .authenticated()
                       .and()
           .csrf()
                     .disable();//都需要认证
    }
}
