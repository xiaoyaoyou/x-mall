package io.xiaoyaoyou.xmall.common.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Component
public class MQSender {
    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String queue, String msg) {
        logger.info("send msg: " + msg);
        amqpTemplate.convertAndSend(queue, msg);
    }
}
