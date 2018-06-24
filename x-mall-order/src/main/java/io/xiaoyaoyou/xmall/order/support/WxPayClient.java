package io.xiaoyaoyou.xmall.order.support;

import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
@Service
public class WxPayClient implements InitializingBean{
    private static Logger logger = LoggerFactory.getLogger(WxPayClient.class);

    Retrofit retrofit;
    WxPayService wxPayServiceImpl;

    @Value("${x-mall.wx-pay.base-url}")
    private String wxPayBaseUrl;

    public interface WxPayService {
        @GET("simulator/wxPay/{orderNo}")
        Call<ResponseBody> wxPay(@Path("orderNo") String orderNo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        retrofit = new Retrofit.Builder()
                .baseUrl(wxPayBaseUrl)
                .build();

        wxPayServiceImpl = retrofit.create(WxPayService.class);

    }

    @Async
    public void wxPay(String orderNo) {
        try {
            wxPayServiceImpl.wxPay(orderNo).execute();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
