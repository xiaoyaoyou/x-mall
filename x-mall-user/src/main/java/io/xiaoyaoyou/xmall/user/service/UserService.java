package io.xiaoyaoyou.xmall.user.service;

import io.xiaoyaoyou.xmall.common.consts.UserConsts;
import io.xiaoyaoyou.xmall.common.entity.User;
import io.xiaoyaoyou.xmall.common.service.UserServiceI;
import io.xiaoyaoyou.xmall.user.dao.UserMapper;
import io.xiaoyaoyou.xmall.user.support.AuthenticationUtil;
import io.xiaoyaoyou.xmall.user.support.UserSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
@Service("userService")
public class UserService implements UserServiceI{
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserSupport userSupport;

    @Override
    public User getUserByUid(int uid) {
        logger.debug("getUserByUid, " + uid);
        return userMapper.queryByUid(uid);
    }

    @Override
    @Transactional
    public User generateUser(String userName, String mobile) {
        User user = new User();
        user.setType(UserConsts.USER_TYPE_REGULAR);
        user.setUsername(userName);
        user.setMobile(mobile);
        user.setPwd(AuthenticationUtil.encryptPwd("111"));

        userMapper.insert(user);

        accountService.createAccount(user.getUid(), UserConsts.ACCOUNT_TYPE_MONEY);
        accountService.createAccount(user.getUid(), UserConsts.ACCOUNT_TYPE_CREDIT);

        return user;
    }

    @Override
    public void batchCreateUserAsync(int num) {
        userSupport.doBatchCreateUserAsync(num);
    }
}
