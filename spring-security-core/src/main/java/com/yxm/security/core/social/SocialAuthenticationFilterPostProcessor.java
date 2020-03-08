package com.yxm.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author yexinming
 * @date 2020/3/5
 **/
public interface SocialAuthenticationFilterPostProcessor {
   void postProcess(SocialAuthenticationFilter socialAuthenticationFilter);
}
