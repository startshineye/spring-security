package com.yxm.security.core.validate.code;

import com.yxm.security.core.validate.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     * @param request
     * @return
     */
    ImageCode generate(ServletWebRequest request);
}
