package com.yxm.security.config;
import com.yxm.security.filter.TimeFilter;
import com.yxm.security.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //1.注入bean
        TimeFilter timeFilter = new TimeFilter();
        registrationBean.setFilter(timeFilter);

        //2.定义url
        List<String> urls = Arrays.asList("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }
}
