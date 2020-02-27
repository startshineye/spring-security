package com.yxm.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author yexinming
 * @date 2020/2/24
 **/
@RestController
public class ValidateCodeController {
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /*@GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        *//**
     * 1.根据随机数生成图片
     * 2.将随机数存到session中
     * 3.将生成图片写到接口的响应中
     *//*
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        *//**
     * 1.根据随机数生成图片
     * 2.将随机数存到session中
     * 3.调用短信服务:将短信发送到指定平台
     *//*
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
        //3.调用短信服务:将短信发送到指定平台,我们封装成如下接口:
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
         smsCodeSender.send(mobile,smsCode.getCode());
    }*/

    //将以上2个服务变成一个服务
    @GetMapping("/code/{type}")
    public void createCode(@PathVariable String type,HttpServletRequest request, HttpServletResponse response) throws Exception {
        ValidateCodeProcessor validateCodeProcessor;
        validateCodeProcessor = validateCodeProcessors.get(type + "ValidateCodeProcessor");
        validateCodeProcessor.create(new ServletWebRequest(request,response));
    }
}
