package io.xiaoyaoyou.xmall.common.service;

import io.xiaoyaoyou.xmall.common.entity.User;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
public interface UserServiceI {
    User getUserByUid(int uid);
    User generateUser(String userName, String mobile);
    void batchCreateUserAsync(int num);
}
