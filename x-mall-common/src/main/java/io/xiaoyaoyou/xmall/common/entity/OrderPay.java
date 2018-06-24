package io.xiaoyaoyou.xmall.common.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by xiaoyaoyou on 2018/5/13.
 */
@Data
public class OrderPay {
    private int id;
    private int orderId;
    private int payType;
    private BigDecimal payAmount;
    private LocalDateTime createTime;
}
