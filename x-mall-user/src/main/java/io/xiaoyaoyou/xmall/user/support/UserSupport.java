package io.xiaoyaoyou.xmall.user.support;

import io.xiaoyaoyou.xmall.common.util.StringUtil;
import io.xiaoyaoyou.xmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
@Service
public class UserSupport {
    @Autowired
    private UserService userService;

    @Async
    public void doBatchCreateUserAsync(int num) {
        if(num <= 0) {
            return;
        }

        for (int i = 1; i <= num; ) {
            try {
                userService.generateUser(StringUtil.getRandomStringByLength(8), StringUtil.getRandomNumByLength(11));
            } catch (Exception e) {//唯一键冲突则跳过继续
                continue;
            }

            i++;
        }
    }
}
