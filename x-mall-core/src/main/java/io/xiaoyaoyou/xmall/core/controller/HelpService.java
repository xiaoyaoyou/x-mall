package io.xiaoyaoyou.xmall.core.controller;

import io.xiaoyaoyou.xmall.common.service.GoodsServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
@Service
public class HelpService {
    Logger logger = LoggerFactory.getLogger(HelpService.class);

    @Autowired
    private GoodsServiceI goodsService;

    @Async
    public void dubboCallTest(int input) {
        int result = goodsService.dubboCallTest(input);
        logger.debug(Thread.currentThread().getId() + ", " + result);
    }
}
