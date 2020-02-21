package com.yxm.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.yxm.security.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
        /**
         * 切片:pointcut里面包含了所有要执行方法的信息:类名 方法名  方法参数等
         */
        System.out.println("time aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is "+arg);
        }

        long start = new Date().getTime();

        Object object = pjp.proceed();//其实就是调用被拦截的方法;类似于Filter中chain.doFilter(request,response)

        System.out.println("time aspect 耗时:"+ (new Date().getTime() - start));

        System.out.println("time aspect end");

        return object;
    }
}
