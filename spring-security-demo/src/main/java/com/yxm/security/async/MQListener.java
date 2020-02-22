package com.yxm.security.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yexinming
 * @date 2020/2/22
 **/
@Component//声明Application应用监听器;模拟线程2
public class MQListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private MQ mqueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(()->{
            while (true){
                if(StringUtils.isNotBlank(mqueue.getCompleteOrder())){
                    //不为空,处理并返回
                    String orderNumber = mqueue.getCompleteOrder();
                    logger.info("订单号处理结果:"+orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("order success!");
                    mqueue.setCompleteOrder(null);//已经处理了 就清空 防止死循环
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
