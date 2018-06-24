package io.xiaoyaoyou.xmall.order.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoyaoyou on 2018/5/18.
 */
@Component
public class OrderCreateMsgErrHandler implements RabbitListenerErrorHandler {
    private static Logger logger = LoggerFactory.getLogger(OrderCreateMsgErrHandler.class);

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        // TODO: 2018/5/18 订单生成失败处理
        logger.debug("order msg handler failed , msg is {}, exception is {}", new String(amqpMessage.getBody()), exception.getMessage());
        return null;
    }
}
