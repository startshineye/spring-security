package com.yxm.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
public class QQProperties extends SocialProperties {

    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}