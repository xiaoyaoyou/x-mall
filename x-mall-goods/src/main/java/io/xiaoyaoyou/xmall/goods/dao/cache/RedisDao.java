package io.xiaoyaoyou.xmall.goods.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.xiaoyaoyou.xmall.common.entity.Goods;
import io.xiaoyaoyou.xmall.common.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;
import java.util.function.Function;

/**
 * Created by xiaoyaoyou on 2018/5/2.
 */
@Service
public class RedisDao implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(RedisDao.class);

    @Autowired
    private JedisPool xmallJedisPoll;

    private RuntimeSchema<Goods> goodsSchema;

    @Override
    public void afterPropertiesSet() throws Exception {
        xmallJedisPoll = new JedisPool(new JedisPoolConfig(), "192.168.1.8", 6379, 2000);

        goodsSchema = RuntimeSchema.createFrom(Goods.class);
    }

    private Goods getGoods(int goodsId, Jedis jedis) {
        boolean innerJedis = jedis == null;
        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }
            String key = SecKillRedisKey.GOODS.key(goodsId);
            //并没有实现哪部序列化操作
            //采用自定义序列化
            //protostuff: pojo.
            byte[] bytes = jedis.get(key.getBytes());
            //缓存重获取到
            if (bytes != null) {
                Goods goods = goodsSchema.newMessage();
                ProtostuffIOUtil.mergeFrom(bytes, goods, goodsSchema);

                return goods;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (innerJedis) {
                returnToPool(jedis);
            }
        }
        return null;
    }

    public Goods getGoodsAndPut(int goodsId, Function<Integer, Goods> getGoodsFromDb) {
        String lockKey = SecKillRedisKey.GOODSLOCK.key(goodsId);
        String lockRequestId = UUID.randomUUID().toString();
        boolean getLock  = false;
        Jedis jedis = null;

        try {
            jedis = xmallJedisPoll.getResource();
            // 循环直到获取到数据
            while (true) {
                Goods goods = getGoods(goodsId, jedis);
                if (goods != null) {
                    return goods;
                }
                // 尝试获取锁。
                // 锁过期时间是防止程序突然崩溃来不及解锁，而造成其他线程不能获取锁的问题。过期时间是业务容忍最长时间。
                getLock = JedisUtil.tryGetDistributedLock(jedis, lockKey, lockRequestId, SecKillRedisKey.GOODSLOCK.expireTime());
                if (getLock) {
                    // 获取到锁，从数据库拿数据, 然后存redis
                    goods = getGoodsFromDb.apply(goodsId);
                    if(goods != null) {
                        putGoods(goods, jedis);
                    }
                    return goods;
                }

                // 获取不到锁，睡一下，等会再出发。sleep的时间需要斟酌，主要看业务处理速度
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (Exception ignored) {
            logger.error(ignored.getMessage(), ignored);
        } finally {
            // 无论如何，最后要去解锁
            try {
                if(getLock) {
                    JedisUtil.releaseDistributedLock(jedis, lockKey, lockRequestId);
                }
            }finally {
                returnToPool(jedis);
            }
        }
        return null;
    }

    private String getGoodsLockKey(int goodsId) {
        return "goods:locks:get:" + goodsId;
    }

    public String putGoods(Goods goods, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }
            String key = SecKillRedisKey.GOODS.key(goods.getId());
            byte[] bytes = ProtostuffIOUtil.toByteArray(goods, goodsSchema,
                    LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
            //超时缓存，1小时
            int timeout = 60 * 60;
            String result = jedis.setex(key.getBytes(), timeout, bytes);

            return result;
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public boolean setGoodsStock(int goodsId, int stock) {
        return setGoodsStock(goodsId, stock, null);
    }

    private boolean setGoodsStock(int goodsId, int stock, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            String key = SecKillRedisKey.GOODSSTOCK.key(goodsId);

            jedis.set(key, String.valueOf(stock));

            return true;
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public int getGoodsStock(int goodsId) {
        return getGoodsStock(goodsId, null);
    }

    private int getGoodsStock(int goodsId, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            String key = SecKillRedisKey.GOODSSTOCK.key(goodsId);

            String v = jedis.get(key);

            return StringUtils.isEmpty(v) ? 0 : Integer.valueOf(v);
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    public long decrGoodsStock(int goodsId) {
        return decrGoodsStock(goodsId, null);
    }

    private long decrGoodsStock(int goodsId, Jedis jedis) {
        boolean innerJedis = jedis == null;

        try {
            if(innerJedis) {
                jedis = xmallJedisPoll.getResource();
            }

            String key = SecKillRedisKey.GOODSSTOCK.key(goodsId);

            return jedis.decr(key);
        } finally {
            if(innerJedis) {
                returnToPool(jedis);
            }
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
