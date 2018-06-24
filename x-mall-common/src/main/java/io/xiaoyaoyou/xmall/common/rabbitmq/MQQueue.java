package io.xiaoyaoyou.xmall.common.rabbitmq;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
public class MQQueue{
    public final static String QUEUE_GOODS_CREATE_ORDER = "queue.goods.create_order";
    public final static String QUEUE_ORDER_SUCCESS_CREDIT = "queue.order.success_credit";
    public final static String QUEUE_ORDER_PAY_SUCCESS_SMS = "queue.order.success_sms";
}
