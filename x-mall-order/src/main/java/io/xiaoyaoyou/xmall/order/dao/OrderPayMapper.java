package io.xiaoyaoyou.xmall.order.dao;

import io.xiaoyaoyou.xmall.common.entity.OrderPay;
import io.xiaoyaoyou.xmall.common.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Mapper
public interface OrderPayMapper extends CrudMapper<OrderPay> {
    List<OrderPay> queryByOrderId(@Param("orderId") int orderId);
}
