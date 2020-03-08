package com.yxm.security.core.social;
import com.yxm.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
@Configuration
@EnableSocial//启用SpringSocial的功能
public class SocialConfig extends SocialConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //connectionFactoryLocator会自动从父类的getUsersConnectionRepository传进来,
        // 这个类是根据接入的服务提供方不同,其有不同的值
        //TextEncryptor会把你插入到数据库里面的数据进行加解密:我们这里使用: Encryptors.noOpText()-不做加解密
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("yxm_");
        logger.info("SocialConfig-->JdbcUsersConnectionRepository:"+repository);
        return repository;
    }

    @Bean
    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator){
        UsersConnectionRepository usersConnectionRepository = getUsersConnectionRepository(connectionFactoryLocator);
        logger.info("SocialConfig-->UsersConnectionRepository:@Bean "+usersConnectionRepository);
        return usersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer mySocialSecurityConfig(){
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        MySpringSocialConfigurer configurer = new MySpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        /**
         * new ProviderSignInUtils(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository)
         * 需要两个参数:
         * 1.connectionFactoryLocator---用来定位connectionFactory的,SpringBoot已经给我们提供好了,我们直接@Autowired注入即可。或者写到上面参数上面,Spring会自动注入
         * 2.UsersConnectionRepository---直接调用方法:getUsersConnectionRepository拿去即可。
         */
         return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }
}
