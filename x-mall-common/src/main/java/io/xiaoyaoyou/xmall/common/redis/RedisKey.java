package io.xiaoyaoyou.xmall.common.redis;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
public interface RedisKey {
    String keyPrefix();

    int expireTime();
}
