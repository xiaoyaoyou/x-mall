package io.xiaoyaoyou.xmall.user.service;

import io.xiaoyaoyou.xmall.common.consts.UserConsts;
import io.xiaoyaoyou.xmall.common.entity.Account;
import io.xiaoyaoyou.xmall.common.service.AccountServiceI;
import io.xiaoyaoyou.xmall.user.dao.AccountMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
@Service("accountService")
public class AccountService implements AccountServiceI, InitializingBean{
    private static int PLATFORM_MONEY_ACCOUNT_ID;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getByUidType(int uid, int type) {
        return accountMapper.queryByUidType(uid, type);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void payMoneyByBalance(int id, BigDecimal amount) {
        accountMapper.freeze(id, amount);

        accountMapper.decreaseFreeze(id, amount);
        accountMapper.increaseBalance(PLATFORM_MONEY_ACCOUNT_ID, amount);
    }

    public void createAccount(int uid, int accountType) {
        Account account = new Account();
        account.setUid(uid);
        account.setType(accountType);
        account.setBalanceAmount(BigDecimal.valueOf(1000));//账户初始金额为1000
        account.setFreezeAmount(BigDecimal.valueOf(0));

        accountMapper.insert(account);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Account platformMoneyAccount = accountMapper.queryByUidType(UserConsts.PLATFORM_UID, UserConsts.ACCOUNT_TYPE_MONEY);
        if(null != platformMoneyAccount) {
            PLATFORM_MONEY_ACCOUNT_ID = platformMoneyAccount.getId();
        }
    }
}
