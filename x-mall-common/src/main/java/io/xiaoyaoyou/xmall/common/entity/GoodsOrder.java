package io.xiaoyaoyou.xmall.common.entity;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsOrder implements Serializable{
    private int id;
    private String orderNo;
    private int uid;
    private int goodsId;
    private BigDecimal originPrice;
    private BigDecimal killPrice;
    private int status;
    private LocalDateTime createTime;
}
