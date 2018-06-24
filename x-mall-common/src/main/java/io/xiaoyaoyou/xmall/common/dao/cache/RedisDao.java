package io.xiaoyaoyou.xmall.common.dao.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
public class RedisDao {
    static final String NXXX_NX = "NX";
    static final String NXXX_XX = "XX";
    static final String EXPX_EX = "EX";
    static final String EXPX_PX = "PX";

    void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
