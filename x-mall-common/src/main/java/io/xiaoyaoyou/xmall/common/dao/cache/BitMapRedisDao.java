package io.xiaoyaoyou.xmall.common.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Service
public class BitMapRedisDao extends RedisDao {
    private static Logger logger = LoggerFactory.getLogger(BitMapRedisDao.class);

    @Autowired
    private JedisPool xmallJedisPoll;

    public boolean setBitMap(BitMapRedisKey bitMapRedisKey, String keySuffix, int offset, boolean value, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            jedis.setbit(bitMapRedisKey.ORDERGOODS.key(keySuffix), offset, value);

            return true;
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public boolean getBitMap(BitMapRedisKey bitMapRedisKey, String keySuffix, int offset, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            return jedis.getbit(bitMapRedisKey.key(keySuffix), offset);
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }
}
