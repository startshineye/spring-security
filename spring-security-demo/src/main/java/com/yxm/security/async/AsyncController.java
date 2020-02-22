package com.yxm.security.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author yexinming
 * @date 2020/2/21
 **/
@RestController
public class AsyncController {
   private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Autowired
    private MQ mqueue;

   @RequestMapping("/order")
   public Callable<String> order() throws Exception{
       /**
        * 异步处理:我们用Callable
        */
       logger.info("主线程开始");
       Callable<String> result = new Callable<String>() {
           @Override
           public String call() throws Exception {
               logger.info("副线程开始");
               Thread.sleep(1000);
               logger.info("副线程返回");
               return "success";
           }
       };
       logger.info("主线程返回");
       return result;
   }

    @RequestMapping("/orderDeferredResult")
    public DeferredResult<String> orderDeferredResult() throws Exception{
        /**
         * 异步处理:我们用Callable
         */
        logger.info("主线程开始");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mqueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);
        logger.info("主线程返回");
        return result;
    }
}
