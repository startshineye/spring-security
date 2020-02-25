package com.yxm.security.core.properties;
/**
 * @author yexinming
 * @date 2020/2/25
 **/
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }
    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
    public SmsCodeProperties getSms() {
        return sms;
    }
    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }
}
