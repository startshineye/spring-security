package com.yxm.security.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yexinming
 * @date 2020/2/22
 **/
@Component//定义为Spring的组件
public class MQ {
    private String placeOrder;//下单请求
    private String completeOrder;//订单完成

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String getPlaceOrder() {
       return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws Exception{
        /**
         * 下单业务逻辑处理:下单之后我们用线程休眠1秒替代应用2出路,然后设置completeOrder为完成
         */
        new Thread(() -> {
            logger.info("接到下单请求, " + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            logger.info("下单请求处理完毕," + placeOrder);
        }).start();

    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
