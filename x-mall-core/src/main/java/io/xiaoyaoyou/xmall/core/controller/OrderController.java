package io.xiaoyaoyou.xmall.core.controller;

import io.xiaoyaoyou.xmall.common.consts.OrderConsts;
import io.xiaoyaoyou.xmall.common.consts.UserConsts;
import io.xiaoyaoyou.xmall.common.entity.Account;
import io.xiaoyaoyou.xmall.common.entity.GoodsOrder;
import io.xiaoyaoyou.xmall.common.exception.ServiceException;
import io.xiaoyaoyou.xmall.common.service.AccountServiceI;
import io.xiaoyaoyou.xmall.common.service.GoodsOrderServiceI;
import io.xiaoyaoyou.xmall.core.common.ApiResponse;
import io.xiaoyaoyou.xmall.core.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private GoodsOrderServiceI goodsOrderService;

    @Autowired
    private AccountServiceI accountService;

    @RequestMapping("/payByBalance/{orderId}")
    public ApiResponse payByBalance(@PathVariable("orderId") int orderId) {
        logger.debug("OrderService doPay, goodsOrderService is " + goodsOrderService);

        GoodsOrder order = goodsOrderService.getOrderById(orderId);
        if(order == null) {
            throw new ServiceException("order pay failed cause the order does not exists, order id: " + orderId);
        }

        logger.debug("order uid is " + order.getUid());

        Account account = accountService.getByUidType(order.getUid(), UserConsts.ACCOUNT_TYPE_MONEY);

        if(account.getBalanceAmount().compareTo(order.getKillPrice()) < 0) {
            throw new ServiceException("order pay failed cause in short of balance, order id: " + orderId);
        }

        accountService.payMoneyByBalance(account.getId(), order.getKillPrice());
        goodsOrderService.orderPaySuccess(order.getOrderNo(), OrderConsts.ORDER_PAY_TYPE_ACCOUNT_BALANCE);

        return BaseResponse.SUCCESS;
    }

    @RequestMapping("/getMsgHandlerCount")
    public ApiResponse getMsgHandlerCount() {
        int result = goodsOrderService.getMsgHandlerCount();

        return new BaseResponse(result);
    }

    @RequestMapping("/resetMsgHandlerCount")
    public ApiResponse resetMsgHandlerCount() {
        goodsOrderService.resetMsgHandlerCount();

        return BaseResponse.SUCCESS;
    }

    @RequestMapping("/dbOrderInsertTest/{num}")
    public ApiResponse dbOrderInsertTest(@PathVariable("num") int num) {
        LocalDateTime beginTime = goodsOrderService.dbInsertTest(num);

        return new BaseResponse<>(beginTime);
    }
}
