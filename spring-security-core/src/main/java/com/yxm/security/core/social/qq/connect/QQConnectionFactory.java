package com.yxm.security.core.social.qq.connect;

import com.yxm.security.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * 把我们之前ServiceProvider提供的信息传给ConnectionFactory即可
     * @param providerId
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId,String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}






























