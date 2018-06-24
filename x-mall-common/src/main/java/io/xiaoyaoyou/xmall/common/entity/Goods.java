package io.xiaoyaoyou.xmall.common.entity;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Goods implements Serializable{
    private int id;
    private String name;
    private BigDecimal originPrice;
    private BigDecimal killPrice;
    private String detail;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
