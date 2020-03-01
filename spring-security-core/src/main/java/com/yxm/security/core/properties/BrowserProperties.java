package com.yxm.security.core.properties;

import com.yxm.security.core.enums.LoginType;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
public class BrowserProperties {
    private String signUpUrl = "/signUp.html";

    private String loginPage = "/login.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 3600;//一小时

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }
}
