package com.yxm.security.core.validate.code;

import com.yxm.security.core.validate.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author yexinming
 * @date 2020/3/5
 **/
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);
}
