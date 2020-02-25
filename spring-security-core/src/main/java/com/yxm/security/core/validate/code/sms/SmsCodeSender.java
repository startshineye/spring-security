package com.yxm.security.core.validate.code.sms;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public interface SmsCodeSender {
    /**
     * 给某个手机发送短信验证码
     * @param mobile
     * @param code
     */
    void send(String mobile,String code);
}
