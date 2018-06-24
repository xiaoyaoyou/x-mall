package io.xiaoyaoyou.xmall.goods.dao.cache;


import io.xiaoyaoyou.xmall.common.redis.RedisKey;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
public enum SecKillRedisKey implements RedisKey {
    GOODS {
        @Override
        public String keyPrefix() {
            return "goods:";
        }

        @Override
        public int expireTime() {
            return 2000;
        }
    },
    GOODSLOCK {
        @Override
        public String keyPrefix() {
            return "goodsLock:";
        }

        @Override
        public int expireTime() {
            return 1000;
        }
    },
    GOODSSTOCK {
        @Override
        public String keyPrefix() {
            return "goodsStock:";
        }

        @Override
        public int expireTime() {
            return 0;
        }
    };

    public String key(int goodsId) {
        return keyPrefix() + goodsId;
    }
}
