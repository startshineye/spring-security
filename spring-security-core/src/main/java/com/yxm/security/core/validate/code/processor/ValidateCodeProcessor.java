package com.yxm.security.core.validate.code.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public interface ValidateCodeProcessor {
    /**
     * 验证码放入session时的前缀
     */
     String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    /**
     * 创建验证码
     * @param request(ServletWebRequest:Spring封装的工具类,封装了request和resposne;不用每次参数传递时候都分别传递:
     * request、response)
     */
    void create(ServletWebRequest request) throws Exception;
}
