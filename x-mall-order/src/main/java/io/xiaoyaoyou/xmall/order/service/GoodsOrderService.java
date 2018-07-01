package io.xiaoyaoyou.xmall.order.service;

import com.google.common.base.Strings;
import io.xiaoyaoyou.xmall.common.consts.OrderConsts;
import io.xiaoyaoyou.xmall.common.dao.cache.BitMapRedisDao;
import io.xiaoyaoyou.xmall.common.dao.cache.BitMapRedisKey;
import io.xiaoyaoyou.xmall.common.entity.GoodsOrder;
import io.xiaoyaoyou.xmall.common.entity.OrderPay;
import io.xiaoyaoyou.xmall.common.rabbitmq.MQQueue;
import io.xiaoyaoyou.xmall.common.rabbitmq.MQSender;
import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import io.xiaoyaoyou.xmall.order.dao.GoodsOrderMapper;
import io.xiaoyaoyou.xmall.order.dao.OrderPayMapper;
import io.xiaoyaoyou.xmall.order.support.OrderNoBuilder;
import io.xiaoyaoyou.xmall.order.support.WxPayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaoyaoyou on 2018/5/8.
 */
@Service("goodsOrderService")
public class GoodsOrderService implements GoodsOrderServiceI {
    private static Logger logger = LoggerFactory.getLogger(GoodsOrderService.class);

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Autowired
    private OrderNoBuilder orderNoBuilder;

    @Autowired
    private MQSender mqSender;

    @Override
    public GoodsOrder getOrderById(int id) {
        return goodsOrderMapper.queryById(id);
    }

    @Autowired
    private OrderPayMapper orderPayMapper;

    @Autowired
    private BitMapRedisDao bitMapRedisDao;

    @Autowired
    private WxPayClient wxPayClient;

    private static AtomicInteger MSG_HANDLER_COUNT = new AtomicInteger(0);

    @Override
    public GoodsOrder getByUidGoodsId(int uid, int goodsId) {
        return goodsOrderMapper.queryByUidGoodsId(uid, goodsId);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void orderPaySuccess(String orderNo, int payType) {
        GoodsOrder order = goodsOrderMapper.queryByOrderNo(orderNo);
        if(Objects.isNull(order)) {
            logger.warn("PaySuccess process cancel cause the order does not esists, orderNo: " + orderNo);
            return;
        }

        if(OrderConsts.ORDER_STATUS_NOT_PAID != order.getStatus()) {
            //防重复调用
            return;
        }

        goodsOrderMapper.updateStatusByNo(orderNo, OrderConsts.ORDER_STATUS_PAID);

        OrderPay orderPay = new OrderPay();
        orderPay.setOrderId(order.getId());
        orderPay.setPayType(payType);
        orderPay.setPayAmount(order.getKillPrice());
        orderPayMapper.insert(orderPay);

        mqSender.send(MQQueue.QUEUE_ORDER_PAY_SUCCESS_SMS, "您的订单已支付成功，我们将尽快为您发货，订单号：" + orderNo);
    }

    @Override
    public int getMsgHandlerCount() {
        return MSG_HANDLER_COUNT.get();
    }

    @Override
    public void resetMsgHandlerCount() {
        MSG_HANDLER_COUNT = new AtomicInteger(0);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void orderCreateMsgHandler(String msg) {
        String[] msgArr = msg.split("\\|");
        logger.info("--------- msgArr length is " + msgArr.length);

        doCreateOrder(msgArr);
    }

    @Transactional(rollbackFor = Throwable.class)
    private void doCreateOrder(String[] msgArr) {
        for(String msg : msgArr) {
            if(Strings.isNullOrEmpty(msg)) {
                continue;
            }

            try {
                String[] orderInfo = msg.split(":");

                if(orderInfo.length < 2) {
                    logger.error("order create msg invaild, msg is: " + msg);
                    continue;
                }

                int uid = Integer.valueOf(orderInfo[0]);
                int goodsId = Integer.valueOf(orderInfo[1]);
                BigDecimal originPrice = new BigDecimal(orderInfo[2]);
                BigDecimal killPrice = new BigDecimal(orderInfo[3]);

//        if(null != goodsOrderMapper.queryByUidGoodsId(uid, goodsId)) {
//            logger.info(uid + " 已秒杀过该商品");
//            return;
//        }
                if(bitMapRedisDao.getBitMap(BitMapRedisKey.ORDERGOODS, String.valueOf(goodsId).intern(), uid, null)) {
                    logger.info(uid + " 已秒杀过该商品");
                    continue;
                }

                GoodsOrder theOrder = new GoodsOrder();
                theOrder.setOrderNo(orderNoBuilder.build(uid));
                theOrder.setUid(uid);
                theOrder.setGoodsId(goodsId);
                theOrder.setOriginPrice(originPrice);
                theOrder.setKillPrice(killPrice);
                theOrder.setStatus(OrderConsts.ORDER_STATUS_NOT_PAID);
                goodsOrderMapper.insert(theOrder);
                bitMapRedisDao.setBitMap(BitMapRedisKey.ORDERGOODS, String.valueOf(goodsId).intern(), uid, true, null);

//        wxPayClient.wxPay(theOrder.getOrderNo());
            }catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Autowired
    private HelpService helpService;

    /**
     * 数据库写性能测试
     */
//    @Override
//    public LocalDateTime dbInsertTest(int num) {
//        BigDecimal price = BigDecimal.valueOf(1);
//
//        logger.debug("dbInsertTest begin");
//        LocalDateTime beginTime = LocalDateTime.now();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i <= num; i++) {
//                    GoodsOrder theOrder = new GoodsOrder();
//                    theOrder.setOriginPrice(price);
//                    theOrder.setKillPrice(price);
//                    theOrder.setStatus(OrderConsts.ORDER_STATUS_NOT_PAID);
////                    theOrder.setOrderNo(StringUtil.getRandomStringByLength(6));
//                    theOrder.setOrderNo(orderNoBuilder.build(i));
//                    theOrder.setUid(i);
//                    theOrder.setGoodsId(i);
//                    helpService.orderInsertAsync(theOrder);
//                }
//            }
//        }).start();
//
//        return beginTime;
//    }

    @Override
    public LocalDateTime dbInsertTest(int num) {
        BigDecimal price = BigDecimal.valueOf(1);

        logger.debug("dbInsertTest begin");
        LocalDateTime beginTime = LocalDateTime.now();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < num; i++) {
                    helpService.orderInsertBatchAsync(i * 10, price);
                }
            }
        }).start();

        return beginTime;
    }
}
