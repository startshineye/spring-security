package com.yxm.security.core.properties;

/**
 * @author yexinming
 * @date 2020/3/6
 **/
public class OAuth2Properties {
    /**
     * jwt密签,加密时候使用这个密签,解密时候也是使用这个密签
     * 自己一定要保存好,别人一旦知道你的秘钥,就可以签发你的jwt令牌,就可以随意进入你系统
     */
    private String jwtSigningKey = "yxm";

    private OAuth2ClientProperties[] clients = {};//默认空数组

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
