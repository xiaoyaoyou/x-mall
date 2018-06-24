package io.xiaoyaoyou.xmall.order.support;


import io.xiaoyaoyou.xmall.common.redis.RedisKey;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
public enum OrderRedisKey implements RedisKey {
    ORDER_NO_SEED {
        @Override
        public String keyPrefix() {
            return "order:no:seed";
        }

        @Override
        public int expireTime() {
            return 0;
        }
    };

    public String key(String keySuffix) {
        return keyPrefix() + keySuffix;
    }
}
