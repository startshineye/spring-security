package com.yxm.security.core.validate.code.processor;
import com.yxm.security.core.validate.ValidateCode;
import com.yxm.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
/**
 * @author yexinming
 * @date 2020/2/25
 **/
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    @Autowired
    private SmsCodeSender smsCodeSender;
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        //使用短信发送平台发送出去
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile,validateCode.getCode());
    }
}
