package io.xiaoyaoyou.xmall.common.service;

import io.xiaoyaoyou.xmall.common.entity.Goods;

import java.util.List;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
public interface GoodsServiceI {
    List<Goods> getGoodsList();
    Goods getGoods(int goodsId);
    void kill(int uid, int goodsId);
    void batchCreateGoodsAsync(int num);
    int getStock(int goodsId);
    void setAllGoodsStock(int stock);
    int dubboCallTest(int input);
}
