package io.xiaoyaoyou.xmall.goods.service;

import io.xiaoyaoyou.xmall.common.consts.UserConsts;
import io.xiaoyaoyou.xmall.common.entity.Goods;
import io.xiaoyaoyou.xmall.common.exception.ServiceException;
import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import io.xiaoyaoyou.xmall.common.service.GoodsServiceI;
import io.xiaoyaoyou.xmall.goods.dao.GoodsMapper;
import io.xiaoyaoyou.xmall.goods.dao.cache.RedisDao;
import io.xiaoyaoyou.xmall.goods.support.GoodsSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Service("goodsService")
public class GoodsService implements GoodsServiceI {
    private static Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private GoodsOrderServiceI goodsOrderService;

    @Autowired
    private GoodsSupport goodsSupport;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.queryByStartAndEndTime(LocalDateTime.now());
    }

    @Override
    public Goods getGoods(int goodsId) {
        return redisDao.getGoodsAndPut(goodsId, goodsMapper::queryById);
    }

    public boolean setStock(int goodsId, int stock) {
        return redisDao.setGoodsStock(goodsId, stock);
    }

    @Override
    public void kill(int uid, int goodsId) {
        if(UserConsts.PLATFORM_UID == uid) {
            throw new ServiceException("平台账号不允许参与秒杀");
        }

        long left = redisDao.decrGoodsStock(goodsId);
        if(left < 0) {
            throw new ServiceException("商品已售罄");
        }

        goodsSupport.sendOrderCreateMsg(uid, goodsId);
    }

    @Override
    public void batchCreateGoodsAsync(int num) {
        goodsSupport.doBatchCreateGoodsAsync(num);
    }

    @Override
    public int getStock(int goodsId) {
        return redisDao.getGoodsStock(goodsId);
    }

    @Override
    public void setAllGoodsStock(int stock) {
        List<Goods> allGoods = goodsMapper.queryAllGoods();

        for (Goods goods : allGoods) {
            redisDao.setGoodsStock(goods.getId(), stock);
        }
    }

    @Override
    public int dubboCallTest(int input) {
        return input;
    }
}
