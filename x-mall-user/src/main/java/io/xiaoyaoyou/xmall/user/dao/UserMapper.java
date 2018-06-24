package io.xiaoyaoyou.xmall.user.dao;

import io.xiaoyaoyou.xmall.common.entity.User;
import io.xiaoyaoyou.xmall.common.mybatis.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Mapper
public interface UserMapper extends CrudMapper<User> {
    User queryByUid(@Param("uid") int uid);
    User queryByMobile(@Param("mobile") String mobile);
}
