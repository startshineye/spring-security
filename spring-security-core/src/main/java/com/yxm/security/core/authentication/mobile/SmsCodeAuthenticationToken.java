package com.yxm.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author yexinming
 * @date 2020/2/26
 **/
public class SmsCodeAuthenticationToken  extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 420L;
    private final Object principal;

    /**
     * 没登录之前,principal我们使用手机号
     * @param mobile
     */
    public SmsCodeAuthenticationToken(String mobile) {
        super((Collection)null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    /**
     * 登录认证之后,principal我们使用用户信息
     * @param principal
     * @param authorities
     */
    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }
    public Object getCredentials() {
        return null;
    }
    public Object getPrincipal() {
        return this.principal;
    }
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
