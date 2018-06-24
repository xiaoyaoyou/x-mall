package io.xiaoyaoyou.xmall.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Data
public class User implements Serializable{
    private int uid;
    private int type;
    private String username;
    private String pwd;
    private String mobile;
    private LocalDateTime createTime;
}
