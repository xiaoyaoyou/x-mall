package io.xiaoyaoyou.xmall.common.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
@Service
public class StrRedisDao extends RedisDao{
    private static Logger logger = LoggerFactory.getLogger(BitMapRedisDao.class);

    @Autowired
    private JedisPool xmallJedisPoll;

    public String get(String key, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            return jedis.get(key);
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public String setNX(String key, String value, int expSecs, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            if(expSecs > 0) {
                return jedis.set(key, value, NXXX_NX, EXPX_EX, expSecs);
            } else {
                return jedis.set(key, value, NXXX_NX);
            }
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public Long increAndGet(String key, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            return jedis.incr(key);
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }
}
