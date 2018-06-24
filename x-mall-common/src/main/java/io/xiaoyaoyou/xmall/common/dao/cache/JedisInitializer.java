package io.xiaoyaoyou.xmall.common.dao.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by xiaoyaoyou on 2018/5/15.
 */
@Configuration
public class JedisInitializer {
    @Value("${spring.redis.host:127.0.0.1}")
    private String redisHost;
    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Bean("xmallJedisPoll")
    public JedisPool initJedisPool() {
        return new JedisPool(new JedisPoolConfig(), redisHost, redisPort, 2000);
    }
}
