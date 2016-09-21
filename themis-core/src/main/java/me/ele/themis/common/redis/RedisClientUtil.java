package me.ele.themis.common.redis;


import me.ele.ejedis.JedisEnhancer;
import me.ele.elog.Log;
import me.ele.elog.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by guoxin on 25/8/2016.
 */
public class RedisClientUtil {

    private static final Log LOG = LogFactory.getLog(RedisClientUtil.class);

    public static ShardedJedisPool shardedJedisPool;

    RedisClientUtil(ShardedJedisPool shardedJedisPool){
        this.shardedJedisPool = shardedJedisPool;
    }


    public static JedisCommands getJedis() {

        //Ejedis  Trace   监控
        JedisEnhancer.getInstance().init();

        ShardedJedis client = shardedJedisPool.getResource();

        if (client == null) {
            return null;
        }
        try{
            return (JedisCommands)  Proxy.newProxyInstance(
                    client.getClass().getClassLoader(),
                    client.getClass().getInterfaces(),
                    new ProxyHandler(client));
        }catch(Exception e){
            LOG.error("redis error:", e);
        }finally{
            client.close();
        }
        return null;
    }


    private static class ProxyHandler implements InvocationHandler {

        private ShardedJedis client;

        public ProxyHandler(ShardedJedis client) {
            super();
            this.client = client;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            boolean broken = false;

            try {
                result = method.invoke(client, args);
            } catch (Exception e) {
                LOG.error("redis error:", e);
                broken = true;
            } finally {
//                if(broken){
//                    shardedJedisPool.returnBrokenResource(client);
//                }else{
//                    shardedJedisPool.returnResource(client);// 向连接池“归还”资源
//                }
//                client.close();
            }
            return result;
        }
    }
}
