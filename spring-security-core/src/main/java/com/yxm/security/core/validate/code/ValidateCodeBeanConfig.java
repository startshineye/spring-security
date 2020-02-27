package com.yxm.security.core.validate.code;

import com.yxm.security.core.properties.SecurityProperties;
import com.yxm.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.yxm.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yexinming
 * @date 2020/2/25
 **/
@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /*
    * 这个配置与我们在ImageCodeGenerator上面加一个注解是类似的,但是这样配置灵活,
    * 可以添加注解:@ConditionalOnMissingBean 作用是:在初始化这个bean的时候,
    * 先到spring容器去查找imageCodeGenerator,如果有一个imageCodeGenerator时候,
    * 就不会再用下面代码去创建
    **/
    @Bean
    @ConditionalOnMissingBean(name="imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){//方法的名字就是放到Spring容器里bean的名字
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){//方法的名字就是放到Spring容器里bean的名字
        return new DefaultSmsCodeSender();
    }
}
