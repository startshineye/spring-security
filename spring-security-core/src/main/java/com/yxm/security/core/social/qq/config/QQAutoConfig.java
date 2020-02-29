package com.yxm.security.core.social.qq.config;

import com.yxm.security.core.properties.QQProperties;
import com.yxm.security.core.properties.SecurityConstants;
import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
@Configuration
/**
 * 我们这里配置的是QQ的连接工厂,那么只有在我们的系统里面只有配置了appId、appSecret时候我们才希望
 * 此配置生效；没有配置了appId、appSecret时候，此配置不起作用
 */
@ConditionalOnProperty(prefix = "yxm.security.social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
