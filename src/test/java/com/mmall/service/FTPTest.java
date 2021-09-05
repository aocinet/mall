package com.mmall.service;

import com.mmall.common.RedisShardedPool;
import com.mmall.util.FTPUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class FTPTest {



    @Test
    public void testFileUpload() throws IOException {
        //FTPUtil ftpUtil = new FTPUtil("127.0.0.1", 21, "geely", "geely");
        List<File> lst = new ArrayList<>();
        File file = new File("C:\\Users\\Aocin\\Desktop\\Temp\\boy.jpg");

        lst.add(file);
        FTPUtil.uploadFile(lst);
        System.out.println("name is : " + file.length());

    }

    @Test
    public void testRedis() throws IOException {

        RedisShardedPool pol = new RedisShardedPool();
    }

}
