package com.yxm.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author yexinming
 * @date 2020/2/26
 **/
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         * 校验逻辑很简单:用SmsCodeAuthenticationToken信息调用UserDetailService去数据库校验
         * 将此对象变成一个已经认证的认证数据
         */
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
         if(user == null){
          throw  new InternalAuthenticationServiceException("无法获取用户信息");
         }

         //.没有错误说明认证成功
        final SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 此方法就是检验AuthenticationManager会使用哪个provider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //此方法就是检验AuthenticationManager会使用哪个provider
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
