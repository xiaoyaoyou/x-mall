package io.xiaoyaoyou.xmall.core.controller;

import io.xiaoyaoyou.xmall.common.entity.User;
import io.xiaoyaoyou.xmall.common.service.UserServiceI;
import io.xiaoyaoyou.xmall.core.common.ApiResponse;
import io.xiaoyaoyou.xmall.core.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiaoyaoyou on 2018/5/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceI userService;

    @RequestMapping("/{uid}")
    public ApiResponse getUser(@PathVariable("uid") int uid) {
        User user = userService.getUserByUid(uid);

        return new BaseResponse<>(user);
    }

    @RequestMapping("/create/{username}/{mobile}")
    public ApiResponse createUser(@PathVariable("username") String username, @PathVariable("mobile") String mobile) {
        User user = userService.generateUser(username, mobile);

        return new BaseResponse<>(user);
    }

    @RequestMapping("/batchCreateUser/{num}")
    public ApiResponse batchCreateUser(@PathVariable("num") int num) {

        userService.batchCreateUserAsync(num);
        return BaseResponse.SUCCESS;
    }
}
