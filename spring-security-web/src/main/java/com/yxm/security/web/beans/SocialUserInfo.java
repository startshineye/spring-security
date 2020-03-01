package com.yxm.security.web.beans;

/**
 * @author yexinming
 * @date 2020/3/1
 **/
public class SocialUserInfo {
    private String providerId;
    private String providerUserId;
    private String nickname;
    private String headimg;
    //getter setter方法

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
