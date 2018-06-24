/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.order.support;

import io.xiaoyaoyou.xmall.common.dao.cache.StrRedisDao;
import io.xiaoyaoyou.xmall.common.exception.CommonException;
import io.xiaoyaoyou.xmall.common.util.Formatters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OrderNoBuilder {
    private static final String SEQUENCE_ID = "goods.order_no";

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private StrRedisDao strRedisDao;

    private Random rand = new Random();

    public String build(int uid) {
        long number = strRedisDao.increAndGet(OrderRedisKey.ORDER_NO_SEED.key(""), null);
        if (number >= 10000000 ) {
            throw new CommonException("sequence overflow, id=" + SEQUENCE_ID);
        }

        int randNum = rand.nextInt(100);
        String suffix = Formatters.getDecimalFormat("00").format(uid % 100) + Formatters.getDecimalFormat("00").format(randNum);

        return Formatters.getDecimalFormat("0000000").format(number) + suffix;
    }
}
