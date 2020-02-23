package com.yxm.security.web.controller;

import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.web.beans.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yexinming
 * @date 2020/2/23
 **/
@RestController
public class WebSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
      //从sesson缓存中拿到SavedRequest
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        //如果是不为空且为html请求登录页面,我们重定向到登录页,否则以封装string的SimpleResponse返回接口401没有授权
        if(savedRequest != null){
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:" + redirectUrl);
            if(StringUtils.endsWithIgnoreCase(redirectUrl,".html")){
                //此处第3个参数就是我们需要 根据多个终端定制化返回。
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        //否则返回401错误
        return new SimpleResponse("访问的服务需要身份认证，请跳转到登录页");
    }
}
