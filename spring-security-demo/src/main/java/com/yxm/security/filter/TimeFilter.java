package com.yxm.security.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
/*@Component*///注入到spring
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("time filter doFilter-->start");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest,servletResponse);
        long end = new Date().getTime();
        System.out.println("time filter 耗时:"+(end-start));
        System.out.println("time filter doFilter-->end");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
