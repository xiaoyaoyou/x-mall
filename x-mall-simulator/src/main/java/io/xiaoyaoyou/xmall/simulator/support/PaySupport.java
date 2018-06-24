package io.xiaoyaoyou.xmall.simulator.support;

import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.Random;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
@Service
public class PaySupport implements InitializingBean{
    Logger logger = LoggerFactory.getLogger(PaySupport.class);

    Random random = new Random();
    Retrofit retrofit;
    PayCallbackService payCallbackServiceImpl;

    public interface PayCallbackService {
        @GET("simulator/wxPayCallback/{orderNo}")
        Call<ResponseBody> callback(@Path("orderNo") String orderNo);
    }

    @Value("${x-mall.wx-pay.callback-base-url}")
    private String callbackUrl;

    @Autowired
    private GoodsOrderServiceI goodsOrderService;

    @Async
    public void doWxPay(String orderNo) {
        logger.debug("do Pay for orderNo " + orderNo);

        try {
            int sleepMillis = random.nextInt(2000) + 1;
            logger.debug("睡" + sleepMillis + "毫秒，模拟微信支付");
            Thread.sleep((long) sleepMillis);

            logger.debug("do callback for orderNo " + orderNo);
            //模拟支付成功回调
            ResponseBody res = payCallbackServiceImpl.callback(orderNo).execute().body();
        } catch (InterruptedException e) {
            logger.debug("do Pay for orderId got error");
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.debug("do Pay for orderId got error");
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        retrofit = new Retrofit.Builder()
                .baseUrl(callbackUrl)
                .build();

        payCallbackServiceImpl = retrofit.create(PayCallbackService.class);
    }
}
