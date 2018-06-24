package io.xiaoyaoyou.xmall.common.service;

import io.xiaoyaoyou.xmall.common.entity.Account;

import java.math.BigDecimal;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
public interface AccountServiceI {
    Account getByUidType(int uid, int Type);
    void payMoneyByBalance(int id, BigDecimal amount);
}
