package me.ele.themis.common.redis;

import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import me.ele.themis.common.redis.RedisClientUtil;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.Collection;

/**
 * Created by guoxin on 25/8/2016.
 */
@Service
public class RedisClient {

    private static final Log LOG = LogFactory.getLog(RedisClient.class);

    public static JedisCommands shardedJedis = RedisClientUtil.getJedis();

        /**
         * 设置单个值 
         *
         * @param key
         * @param value
         * @return
         */
        public String set(String key, String value) {
            return shardedJedis.set(key, value);
        }

        /**
         * 获取单个值 
         *
         * @param key
         * @return
         */
        public String get(String key) {
            return shardedJedis.get(key);
        }

        public Boolean exists(String key) {
            return shardedJedis.exists(key);
        }

        public String type(String key) {
            return shardedJedis.type(key);
        }

        /**
         * 在某段时间后失效 
         *
         * @param key
         * @param
         * @return
         */
        public Long expire(String key, int seconds) {
            return shardedJedis.expire(key, seconds);
        }

        /**
         * 在某个时间点失效 
         *
         * @param key
         * @param unixTime
         * @return
         */
        public Long expireAt(String key, long unixTime) {
            return shardedJedis.expireAt(key, unixTime);
        }

        public Long ttl(String key) {
            return shardedJedis.ttl(key);
        }

        public boolean setbit(String key, long offset, boolean value) {

            return shardedJedis.setbit(key, offset, value);
        }

        public boolean getbit(String key, long offset) {
            return shardedJedis.getbit(key, offset);
        }

}
