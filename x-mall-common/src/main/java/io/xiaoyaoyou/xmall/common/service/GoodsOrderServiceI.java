package io.xiaoyaoyou.xmall.common.service;


import io.xiaoyaoyou.xmall.common.entity.GoodsOrder;

import java.time.LocalDateTime;

/**
 * Created by xiaoyaoyou on 2018/5/6.
 */
public interface GoodsOrderServiceI {
    GoodsOrder getOrderById(int id);
    GoodsOrder getByUidGoodsId(int uid, int goodsId);
    void orderPaySuccess(String orderNo, int payType);
    int getMsgHandlerCount();
    void resetMsgHandlerCount();
    LocalDateTime dbInsertTest(int num);
}
