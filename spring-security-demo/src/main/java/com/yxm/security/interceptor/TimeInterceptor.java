package com.yxm.security.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.logging.Handler;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@Component //注意光声明为Component不能让其起作用  还需要在:WebConfig 中继承(extends) WebMvcConfigurerAdapter
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.在控制器方法调用前执行
         * 2.Interceptor比Filter的一个优势是具有:handler
         * 3.return 返回值决定了后面方法是否要执行
         */
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        System.out.println(handlerMethod.getBean().getClass().getName());//打印出类名
        System.out.println(handlerMethod.getMethod().getName());//打印出方法名
        request.setAttribute("startTime",new Date().getTime());
        System.out.println("TimeInterceptor-->preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /**
         * 在控制器方法调用成功后执行:如果方法抛出了异常将不会执行此方法
         */
        System.out.println("TimeInterceptor-->postHandle");
        Long start = (Long)request.getAttribute("startTime");
        System.out.println("TimeInterceptor 耗时:"+(new Date().getTime()-start));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        /**
         * 控制器方法不管成功还是失败都会进入到此方法
         */
        System.out.println("TimeInterceptor-->afterCompletion");
        Long start = (Long)request.getAttribute("startTime");
        System.out.println("TimeInterceptor 耗时:"+(new Date().getTime()-start));
        System.out.println("TimeInterceptor ex is :"+e);
    }
}
