package io.xiaoyaoyou.xmall.goods.support;

import com.google.common.base.Strings;
import io.xiaoyaoyou.xmall.common.entity.Goods;
import io.xiaoyaoyou.xmall.common.rabbitmq.MQQueue;
import io.xiaoyaoyou.xmall.common.rabbitmq.MQSender;
import io.xiaoyaoyou.xmall.common.util.StringUtil;
import io.xiaoyaoyou.xmall.goods.dao.GoodsMapper;
import io.xiaoyaoyou.xmall.goods.dao.cache.RedisDao;
import io.xiaoyaoyou.xmall.goods.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
@Service
public class GoodsSupport implements InitializingBean{
    Logger logger = LoggerFactory.getLogger(GoodsSupport.class);

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MQSender mqSender;

    private ArrayBlockingQueue<String> orderCreateMsgBuffer;

    @Autowired
    private Executor executor;

    @Async
    public void sendOrderCreateMsg(int uid, int goodsId) {
        Goods goods = goodsService.getGoods(goodsId);

        try {
            orderCreateMsgBuffer.put(orderCreateMsg(uid, goodsId, goods.getOriginPrice(), goods.getKillPrice()));
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
//        mqSender.send(MQQueue.QUEUE_GOODS_CREATE_ORDER, orderCreateMsg(uid, goodsId, goods.getOriginPrice(), goods.getKillPrice()));
    }

    private String orderCreateMsg(int uid, int goodsId, BigDecimal originPrice, BigDecimal killPrice) {
        return uid + ":" + goodsId + ":" + originPrice + ":" + killPrice;
    }

    @Async
    public void doBatchCreateGoodsAsync(int num) {
        if(num <= 0) {
            return;
        }

        Random random = new Random();

        for (int i = 1; i <= num; i++) {
            try {
                Goods goods = new Goods();
                goods.setName("小米" + StringUtil.getRandomStringByLength(5));
                goods.setOriginPrice(BigDecimal.valueOf(random.nextInt(100) + 400));
                goods.setKillPrice(BigDecimal.valueOf(random.nextInt(100) + 100));
                goods.setDetail(StringUtil.getRandomStringByLength(30));
                goods.setStartTime(LocalDateTime.now());
                goods.setEndTime(LocalDateTime.now().plusDays(30));

                goodsMapper.insert(goods);

                redisDao.setGoodsStock(goods.getId(), 100);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                continue;
            }
        }
    }

    @Override
    @Async
    public void afterPropertiesSet() throws Exception {
        orderCreateMsgBuffer = new ArrayBlockingQueue(1024);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    orderCreateMsgWorkerStart();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    private void orderCreateMsgWorkerStart() throws InterruptedException {
        int processNum = 0;
        String packageMsg = "";

        while (true) {
            String msg = orderCreateMsgBuffer.take();

            packageMsg += (Strings.isNullOrEmpty(packageMsg) ? "" : "|") + msg;
            if(--processNum <= 0) {
                mqSender.send(MQQueue.QUEUE_GOODS_CREATE_ORDER, packageMsg);

                packageMsg = "";
                processNum = orderCreateMsgBuffer.size() > 10 ? 10 : orderCreateMsgBuffer.size();
            }
        }
    }
}
