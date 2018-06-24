package io.xiaoyaoyou.xmall.common.dao.cache;


import io.xiaoyaoyou.xmall.common.redis.RedisKey;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
public enum BitMapRedisKey implements RedisKey {
    ORDERGOODS {
        @Override
        public String keyPrefix() {
            return "order:goods:";
        }

        @Override
        public int expireTime() {
            return 2000;
        }
    };

    public String key(String keySuffix) {
        return keyPrefix() + keySuffix;
    }
}
