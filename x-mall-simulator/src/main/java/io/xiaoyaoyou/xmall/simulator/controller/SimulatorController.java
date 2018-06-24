package io.xiaoyaoyou.xmall.simulator.controller;

import io.xiaoyaoyou.xmall.common.consts.OrderConsts;
import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import io.xiaoyaoyou.xmall.simulator.support.PaySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
@RestController
@RequestMapping("/simulator")
public class SimulatorController {
    Logger logger = LoggerFactory.getLogger(SimulatorController.class);

    @Autowired
    private PaySupport paySupport;

    @Autowired
    private GoodsOrderServiceI goodsOrderService;

    @RequestMapping("/wxPay/{orderNo}")
    public String wxPay(@PathVariable("orderNo") String orderNo) {
        logger.debug("wxPay order no is " + orderNo);

        paySupport.doWxPay(orderNo);

        return "ok, wait callback";
    }

    @RequestMapping("/wxPayCallback/{orderNo}")
    public String wxPayCallback(@PathVariable("orderNo") String orderNo) {
        logger.debug("wxPayCallback order no is " + orderNo);

        goodsOrderService.orderPaySuccess(orderNo, OrderConsts.ORDER_PAY_TYPE_WX);

        return "ok callback accept";
    }
}
