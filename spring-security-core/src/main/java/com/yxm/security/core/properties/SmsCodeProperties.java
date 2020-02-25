package com.yxm.security.core.properties;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public class SmsCodeProperties {
    private int length = 6;//长度
    private int expireIn = 60;//过期时间
    private String url;//要处理的url

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getExpireIn() {
        return expireIn;
    }
    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
