package com.yxm.security.app;

import com.yxm.security.core.properties.OAuth2ClientProperties;
import com.yxm.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yexinming
 * @date 2020/3/2
 **/
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore redisTokenStore;

    @Autowired(required = false) //因为这个类在JwtTokenConfig有效情况下才有效
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 系统端点配置：endpoints
         * 1.使用我们自己的授权管理器(AuthenticationManager)和自定义的用户详情服务(UserDetailsService)
         */
         endpoints.tokenStore(redisTokenStore)
                 .authenticationManager(authenticationManager)
                 .userDetailsService(userDetailsService);

         if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
             /**
              * 1.由于我们之前通过DefaultTokenService创建的AccessToken默认是通过UUID来创建的，并且主框架没有给我们提供共有的创建accessToken方法
              * 2.创建的默认方法: private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken),
              * 所以我们智能在endpoints里面配置增强器。
              * 3.为了满足1，2；我们需要创建增强器链,来将jwtAccessTokenConverter和jwtTokenEnhancer连接起来。
              */
             TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
              List<TokenEnhancer> enhancers = new ArrayList<>();
             enhancers.add(jwtTokenEnhancer);
             enhancers.add(jwtAccessTokenConverter);
             enhancerChain.setTokenEnhancers(enhancers);

             endpoints
                     .tokenEnhancer(enhancerChain)
                     .accessTokenConverter(jwtAccessTokenConverter);
         }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 系统第三方客户端配置:
         * 所谓的客户端就是有哪些应用会访问我们的系统，
         * 我们的认证服务器会决定给哪些第三方应用client去发送令牌。
         * 如果这个配置后我们之前配置文件中配置的clientId和clientSecret将不会起作用了
         */
        //目前我们的应用场景是在我们的app和我们的前端；我们不允许第三方来注册，所以用内存
       /* clients.inMemory().withClient("yxm")
                .secret("yxmsecret")
                .accessTokenValiditySeconds(7200)//发出去的令牌,有效期是多少? 这里设置为2小时
                .authorizedGrantTypes("refresh_token","password")//针对当前应用客户端：yxm,所能支持的授权模式是哪些?之前设置有4种类加上刷新总共5种:这里只支持配置的:"refresh_token","password"。
                .scopes("all","read","write")//发出去的权限有哪些?之前前端请求携带了scoope,此配置的scope用来指定前端发送scope的值必须在配置的里面或者不携带scope;默认为此处配置的scope
                .and()
                .withClient("startshineye")
                .secret("startshineyesecret")
                .accessTokenValiditySeconds(3600)
                .authorizedGrantTypes("password")
                .scopes("read");*/
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {//判断我们的配置是否为空
            for (OAuth2ClientProperties client:securityProperties.getOauth2().getClients()){
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                        .scopes("all");
            }
        }
    }
}
