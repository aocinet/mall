package com.mmall.common;

import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;
@Component
public class RedisShardedPool {
    private static ShardedJedisPool pool;//sharded jedis连接池

    //private static Integer maxTotal;
    private static Integer maxTotal = Const.REDIS_CONFIG.REDIS_MAXTOTAL;
//    = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20")); //最大连接数

    private static Integer maxIdle = Const.REDIS_CONFIG.REDIS_MAXIDLE;
    //private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));//在jedispool中最大的idle状态(空闲的)的jedis实例的个数

    private static Integer minIdle = Const.REDIS_CONFIG.REDIS_MINIDLE;
    //private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));//在jedispool中最小的idle状态(空闲的)的jedis实例的个数

    private static Boolean testOnBorrow = Const.REDIS_CONFIG.REDIS_TESTONBORROW;
    //= Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。

    private static Boolean testOnReturn = Const.REDIS_CONFIG.REDIS_TESTONRETURN;
    //= Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    private static String redis1Ip = Const.REDIS_CONFIG.REDIS_REDIS1IP;
    //= PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Const.REDIS_CONFIG.REDIS_REDIS1PORT;
    //= Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = Const.REDIS_CONFIG.REDIS_REDIS2IP;
    //= PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Const.REDIS_CONFIG.REDIS_REDIS2PORT;

//    public RedisShardedPool(Integer maxTotal) {
//        this.maxTotal = maxTotal;
//
//    }
    //= Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

//    @Value("${spring.redis.max.total}")
//    public void setMaxTotal(Integer maxTotal) {
//        System.out.println("maxtotal:");
//        RedisShardedPool.maxTotal = maxTotal;
//        System.out.println("maxtotal:" + RedisShardedPool.maxTotal);
//    }
//
//    @Value("${spring.redis.max.idle}")
//    public void setMaxIdle(Integer maxIdle) {
//        RedisShardedPool.maxIdle = maxIdle;
//    }

//    @Value("${spring.redis.min.idle}")
//    public void setMinIdle(Integer minIdle) {
//        RedisShardedPool.minIdle = minIdle;
//    }
//
//    @Value("${spring.redis.test.borrow}")
//    public void setTestOnBorrow(Boolean testOnBorrow) {
//        RedisShardedPool.testOnBorrow = testOnBorrow;
//    }
//
//    @Value("${spring.redis.test.return}")
//    public void setTestOnReturn(Boolean testOnReturn) {
//        RedisShardedPool.testOnReturn = testOnReturn;
//    }
//
//    @Value("${spring.redis1.ip}")
//    public void setRedis1Ip(String redis1Ip) {
//        RedisShardedPool.redis1Ip = redis1Ip;
//    }
//
//    @Value("${spring.redis1.port}")
//    public void setRedis1Port(Integer redis1Port) {
//        RedisShardedPool.redis1Port = redis1Port;
//    }
//
//    @Value("${spring.redis2.ip}")
//    public void setRedis2Ip(String redis2Ip) {
//        RedisShardedPool.redis2Ip = redis2Ip;
//    }
//
//    @Value("${spring.redis2.port}")
//    public void setRedis2Port(Integer redis2Port) {
//        RedisShardedPool.redis2Port = redis2Port;
//    }


    public static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        System.out.println("redis2Ip:" + redis2Ip);
        System.out.println("maxtoal:" + maxTotal);
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip,redis1Port,1000*2);

        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,1000*2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);

        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        pool = new ShardedJedisPool(config,jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }


    static{
        initPool();
    }

    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {

        ShardedJedis jedis = pool.getResource();

        for(int i =0;i<10;i++){
            jedis.set("key"+i,"value"+i);
        }
        returnResource(jedis);
//        pool.destroy();//临时调用，销毁连接池中的所有连接

        System.out.println("end");
    }




}
