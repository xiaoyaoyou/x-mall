package io.xiaoyaoyou.xmall.user.rabbitsmq;

import io.xiaoyaoyou.xmall.common.rabbitmq.MQQueue;
import io.xiaoyaoyou.xmall.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Component
public class MQReceiver {
    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private UserService userService;

    @RabbitListener(queues= MQQueue.QUEUE_ORDER_SUCCESS_CREDIT)
    public void receive(String msg) {
        logger.info("curr thread is " + Thread.currentThread().getName() + ", receive msg: " + msg);
    }
}
