package io.xiaoyaoyou.xmall.goods.dao;

import io.xiaoyaoyou.xmall.common.entity.Goods;
import io.xiaoyaoyou.xmall.common.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Mapper
public interface GoodsMapper extends CrudMapper<Goods> {
    List<Goods> queryByStartAndEndTime(@Param("currTime") LocalDateTime currTime);
    List<Goods> queryAllGoods();
}
