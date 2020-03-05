package com.yxm.security.app.authentication.openid;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 校验:OpenIdAuthenticationToken
 * @author yexinming
 * @date 2020/3/5
 **/
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;

    private UsersConnectionRepository usersConnectionRepository;//Spring Security自己管理的类 不需要引入

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        /**
         * 校验时候,我们的处理逻辑是从:之前创建的表:yxm_UserConnection
         * 1.从未授权的authentication获取基本信息
         * 2.根据这些基本信息进行校验
         * 3.校验通过:构建已经授权的Authentication然后返回,校验不通过抛出异常信息
         */
        //1.从未授权的authentication获取基本信息
        Set<String> providerUserIdSet = new HashSet<>();
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
        String openId = (String) authenticationToken.getPrincipal();
        providerUserIdSet.add(openId);

        //2.根据这些基本信息进行校验
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIdSet);
        if(CollectionUtils.isEmpty(userIds) || userIds.size() != 1){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();
        UserDetails user = userDetailsService.loadUserByUserId(userId);
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        //3.校验通过:构建已经授权的Authentication然后返回,校验不通过抛出异常信息
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}
