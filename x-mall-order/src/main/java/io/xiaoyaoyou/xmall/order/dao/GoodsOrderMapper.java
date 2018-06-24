package io.xiaoyaoyou.xmall.order.dao;

import io.xiaoyaoyou.xmall.common.entity.GoodsOrder;
import io.xiaoyaoyou.xmall.common.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Mapper
public interface GoodsOrderMapper extends CrudMapper<GoodsOrder> {
    void updateStatusByNo(@Param("orderNo") String orderNo, @Param("status") int status);
    void updateStatusById(@Param("id") int id, @Param("status") int status);
    GoodsOrder queryByUidGoodsId(@Param("uid") int uid, @Param("goodsId") int goodsId);
    GoodsOrder queryByOrderNo(@Param("orderNo") String orderNo);
}
