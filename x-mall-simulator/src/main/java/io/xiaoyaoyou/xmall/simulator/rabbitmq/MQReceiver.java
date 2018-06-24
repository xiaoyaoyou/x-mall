package io.xiaoyaoyou.xmall.simulator.rabbitmq;

import io.xiaoyaoyou.xmall.common.rabbitmq.MQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Component
public class MQReceiver {
    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues= MQQueue.QUEUE_ORDER_PAY_SUCCESS_SMS)
    public void receive(String msg) {
        logger.info("订单支付成功消息：" + msg);
    }
}
