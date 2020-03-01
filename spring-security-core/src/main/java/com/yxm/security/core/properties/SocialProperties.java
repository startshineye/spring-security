package com.yxm.security.core.properties;

/**
 * @author yexinming
 * @date 2020/2/29
 **/
public class SocialProperties {
    /**
     * SocialAuthenticationFilter的默认过滤器url是:/auth
     */
    private String  filterProcessesUrl="/auth";
    private QQProperties qq = new QQProperties();

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
