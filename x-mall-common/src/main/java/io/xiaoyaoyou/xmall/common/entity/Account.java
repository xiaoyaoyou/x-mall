package io.xiaoyaoyou.xmall.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
@Data
public class Account implements Serializable{
    private int id;
    private int uid;
    private int type;
    private BigDecimal balanceAmount;
    private BigDecimal freezeAmount;
}
