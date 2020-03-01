package com.yxm.security.core.social.qq;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author yexinming
 * @date 2020/3/1
 **/
public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public MySpringSocialConfigurer(String filterProcessesUrl){
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        /**
         * 1.我们覆盖SpringSocialConfigurer的postProcess
         * 2.里面的object就是我们之前：SocialAuthenticationFilter
         */
        //1.获取父类处理的结果
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        //2.设置FilterProcessesUrl，由于我们每个应用传递的FilterProcessesUrl可能是不一样的,所以我们将其作为可配置的
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return super.postProcess(object);
    }
}
