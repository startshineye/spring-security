package com.yxm.security.core.validate.code.sms;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机:"+mobile+" 发送短信验证码:"+code);
    }
}
