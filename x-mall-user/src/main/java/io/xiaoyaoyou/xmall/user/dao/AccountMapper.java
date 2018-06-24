package io.xiaoyaoyou.xmall.user.dao;

import io.xiaoyaoyou.xmall.common.entity.Account;
import io.xiaoyaoyou.xmall.common.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
public interface AccountMapper extends CrudMapper<Account>{
    Account queryByUidType(@Param("uid") int uid, @Param("type") int type);
    void freeze(@Param("id") int id, @Param("amount") BigDecimal amount);
    void cancelFreeze(@Param("id") int id, @Param("amount") BigDecimal amount);
    void decreaseFreeze(@Param("id") int id, @Param("amount") BigDecimal amount);
    void decreaseBalance(@Param("id") int id, @Param("amount") BigDecimal amount);
    void increaseBalance(@Param("id") int id, @Param("amount") BigDecimal amount);
}
