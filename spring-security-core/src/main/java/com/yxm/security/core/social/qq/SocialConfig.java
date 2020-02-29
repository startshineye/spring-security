package com.yxm.security.core.social.qq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
@Configuration
@EnableSocial//启用SpringSocial的功能
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //connectionFactoryLocator会自动从父类的getUsersConnectionRepository传进来,
        // 这个类是根据接入的服务提供方不同,其有不同的值
        //TextEncryptor会把你插入到数据库里面的数据进行加解密:我们这里使用: Encryptors.noOpText()-不做加解密
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("yxm_");
        return repository;
    }

    @Bean
    public SpringSocialConfigurer mySocialSecurityConfig(){
      return new SpringSocialConfigurer();
    }
}
