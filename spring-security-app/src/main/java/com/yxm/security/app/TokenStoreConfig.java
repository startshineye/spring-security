package com.yxm.security.app;

import com.yxm.security.app.jwt.MyJwtTokenEnhancer;
import com.yxm.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author yexinming
 * @date 2020/3/6
 **/
@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "yxm.security.oauth2",name = "storeType",havingValue = "redis")
    public TokenStore redisTokenStore(){
      return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    /**
     * 前缀(prefix):代表的是以"."隔开的配置文件中,最后一个点前面的所有字符串。
     * name:代表的是以"."隔开的配置文件中，最后一个点后面的字符串。
     */
    @ConditionalOnProperty(prefix = "yxm.security.oauth2",name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfig{

        @Autowired
        private SecurityProperties securityProperties;

        /**
         * 只负责token存储,不管token生成
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore(){
          return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 负责token生成过程中的处理的
         * JwtAccessTokenConverter功能可以进行密签,就是进行签名。
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //1.设置签名秘钥
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return accessTokenConverter;
        }

        @Bean
        @ConditionalOnMissingBean(name="jwtTokenEnhancer")//业务系统自己可以添加一个:jwtTokenEnhancer来覆盖
        public TokenEnhancer jwtTokenEnhancer(){
          return new MyJwtTokenEnhancer();
        }
    }
}
