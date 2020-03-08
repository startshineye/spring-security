package com.yxm.security.core.properties;

/**
 * @author yexinming
 * @date 2020/3/6
 **/
public class OAuth2ClientProperties {
    //我们把:ClientDetailsServiceConfigurer相关配置加进来
    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
