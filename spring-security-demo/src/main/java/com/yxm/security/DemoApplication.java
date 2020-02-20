package com.yxm.security;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yexinming
 * @date 2020/2/19
 **/
@SpringBootApplication
@RestController
public class DemoApplication {
    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello spring security";
    }
}
