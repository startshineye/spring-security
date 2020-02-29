package com.yxm.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
//此类读取配置文件里所有以yxm.security开头的配置
@ConfigurationProperties(prefix = "yxm.security")
public class SecurityProperties {
    //其中yxm.security.browser开头的配置否会读取到BrowserProperties中
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social =  new SocialProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }
}
