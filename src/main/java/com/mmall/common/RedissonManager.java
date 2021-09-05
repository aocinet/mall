package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by tq
 */
@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }


    private static String redis1Ip = Const.REDIS_CONFIG.REDIS_REDIS1IP;
    private static Integer redis1Port = Const.REDIS_CONFIG.REDIS_REDIS1PORT;
    private static String redis2Ip = Const.REDIS_CONFIG.REDIS_REDIS2IP;
    private static Integer redis2Port = Const.REDIS_CONFIG.REDIS_REDIS2PORT;

//    @Value("${spring.redis1.ip}")
//    public void setRedis1Ip(String redis1Ip) {
//        RedissonManager.redis1Ip = redis1Ip;
//    }
//
//    @Value("${spring.redis1.port}")
//    public void setRedis1Port(Integer redis1Port) {
//        RedissonManager.redis1Port = redis1Port;
//    }
//
//    @Value("${spring.redis2.ip}")
//    public void setRedis2Ip(String redis2Ip) {
//        RedissonManager.redis2Ip = redis2Ip;
//    }
//
//    @Value("${spring.redis2.port}")
//    public void setRedis2Port(Integer redis2Port) {
//        RedissonManager.redis2Port = redis2Port;
//    }



    @PostConstruct
    private void init(){
        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString());

            redisson = (Redisson) Redisson.create(config);

            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("redisson init error",e);
        }
    }



}
