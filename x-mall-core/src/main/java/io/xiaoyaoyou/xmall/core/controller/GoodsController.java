package io.xiaoyaoyou.xmall.core.controller;

import io.xiaoyaoyou.xmall.common.dao.cache.BitMapRedisDao;
import io.xiaoyaoyou.xmall.common.dao.cache.BitMapRedisKey;
import io.xiaoyaoyou.xmall.common.entity.Goods;
import io.xiaoyaoyou.xmall.common.exception.ServiceException;
import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import io.xiaoyaoyou.xmall.common.service.GoodsServiceI;
import io.xiaoyaoyou.xmall.core.common.ApiResponse;
import io.xiaoyaoyou.xmall.core.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by xiaoyaoyou on 2018/5/8.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController{
    Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsOrderServiceI goodsOrderService;

    @Autowired
    private GoodsServiceI goodsService;

    @Autowired
    private BitMapRedisDao bitMapRedisDao;

    Random random = new Random();
    private static final int MIN_UID = 1003;
    private static final int USER_NUM = 10400;

    private static final int MIN_GOODS_ID = 1000;
    private static final int GOODS_NUM = 100;

    @RequestMapping("/kill/{uid}/{goodsId}")
    public ApiResponse kill(@PathVariable("uid") int uid, @PathVariable("goodsId") int goodsId) {
        if(uid < 0) {
            uid = mockUid();
        }
        if(goodsId < 0) {
            goodsId = mockGoodsId();
        }

        if(bitMapRedisDao.getBitMap(BitMapRedisKey.ORDERGOODS, String.valueOf(goodsId).intern(), uid, null)) {
            throw new ServiceException("一个人只能秒杀一件");
        }

        goodsService.kill(uid, goodsId);

        return BaseResponse.SUCCESS;
    }

    private int mockUid() {
        return random.nextInt(USER_NUM) + MIN_UID;
    }

    private int mockGoodsId() {
        return random.nextInt(GOODS_NUM) + MIN_GOODS_ID;
    }

    @RequestMapping("/goodsList")
    public ApiResponse goodsList() {
        List<Goods> result = goodsService.getGoodsList();

        return new BaseResponse(result);
    }

    @RequestMapping("/batchCreateGoods/{num}")
    public ApiResponse batchCreateUser(@PathVariable("num") int num) {
        goodsService.batchCreateGoodsAsync(num);
        return BaseResponse.SUCCESS;
    }

    @RequestMapping("/setAllGoodsStock/{stock}")
    public ApiResponse setAllGoodsStock(@PathVariable("stock") int stock) {
        goodsService.setAllGoodsStock(stock);

        return BaseResponse.SUCCESS;
    }

    @Autowired
    private HelpService helpService;

    /**
     * duboo 调用性能测试，异步情况下10000次调用花费时间
     * @return
     */
    @RequestMapping("/dubboCallTest")
    public ApiResponse dubboCallTest() {
        logger.debug("-------------dubboCallTest begin");
        for (int i = 1; i < 10000; i++) {
            helpService.dubboCallTest(i);
        }

        return BaseResponse.SUCCESS;
    }
}
