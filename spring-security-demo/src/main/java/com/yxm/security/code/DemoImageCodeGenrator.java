package com.yxm.security.code;

import com.yxm.security.core.validate.ImageCode;
import com.yxm.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
//@Component("imageCodeGenrator")
public class DemoImageCodeGenrator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        return null;
    }
}
