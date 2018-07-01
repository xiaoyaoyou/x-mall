package io.xiaoyaoyou.xmall.order.rabbitmq;

import io.xiaoyaoyou.xmall.common.exception.ServiceException;
import io.xiaoyaoyou.xmall.common.rabbitmq.MQQueue;
import io.xiaoyaoyou.xmall.order.service.GoodsOrderService;
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
    private GoodsOrderService goodsOrderService;

    @RabbitListener(queues= MQQueue.QUEUE_GOODS_CREATE_ORDER, errorHandler="orderCreateMsgErrHandler")
    public void receive(String msg) {
        goodsOrderService.orderCreateMsgHandler(msg);
    }
}
