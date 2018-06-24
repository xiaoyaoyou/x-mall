package io.xiaoyaoyou.xmall.order.service;

import io.xiaoyaoyou.xmall.common.consts.OrderConsts;
import io.xiaoyaoyou.xmall.common.entity.GoodsOrder;
import io.xiaoyaoyou.xmall.order.dao.GoodsOrderMapper;
import io.xiaoyaoyou.xmall.order.support.OrderNoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
@Service
public class HelpService {
    private static Logger logger = LoggerFactory.getLogger(HelpService.class);

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Autowired
    private OrderNoBuilder orderNoBuilder;

    @Async
    @Transactional(rollbackFor = Throwable.class)
    public void orderInsertAsync(GoodsOrder theOrder) {
        goodsOrderMapper.insert(theOrder);
        logger.debug("insert over , " + theOrder.getOrderNo());
    }

    @Async
    @Transactional(rollbackFor = Throwable.class)
    public void orderInsertBatchAsync(int i, BigDecimal price) {
        for (int j = 1; j <= 10; j++) {
            GoodsOrder theOrder = new GoodsOrder();
            theOrder.setOriginPrice(price);
            theOrder.setKillPrice(price);
            theOrder.setStatus(OrderConsts.ORDER_STATUS_NOT_PAID);
            theOrder.setOrderNo(orderNoBuilder.build(i + j));
            theOrder.setUid(i + j);
            theOrder.setGoodsId(i + j);

            goodsOrderMapper.insert(theOrder);
        }

        logger.debug("insert over , " + i);
    }
}
