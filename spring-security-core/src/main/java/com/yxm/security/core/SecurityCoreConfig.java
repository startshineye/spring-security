package com.yxm.security.core;

import com.yxm.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
@Configuration//配置类
@EnableConfigurationProperties(SecurityProperties.class)//让配置类生效
public class SecurityCoreConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
