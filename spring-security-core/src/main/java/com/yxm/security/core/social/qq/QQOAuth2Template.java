package com.yxm.security.core.social.qq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author yexinming
 * @date 2020/3/1
 **/
public class QQOAuth2Template extends OAuth2Template {
    private static final Logger logger = LoggerFactory.getLogger(QQOAuth2Template.class);

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        //设置useParametersForClientAuthentication为true,这样发请求时候才会带着client_id、client_secret
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        //1.返回字符串而不是对象
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        logger.info("获取accessToken的响应:"+accessTokenUrl);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        //1.先拿到父类创建结果:我们需要使用父类的部分功能,只是需要在此基础上添加而已
        RestTemplate restTemplate = super.createRestTemplate();
        //2.获取消息转换类
        List<HttpMessageConverter<?>> messageConverters =
                restTemplate.getMessageConverters();
        //3.添加自定义的消息转换类。
         messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return  restTemplate;
    }
}
