package com.mmall.service;

import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest {
    @Autowired
    IUserService userService;

    @Test
    @Transactional
    public void testUserLogin () {
//        User user  = new User();
//        user.setUsername("abs");
//        user.setPassword("123");
//        user.setPhone("123");
//        user.setAnswer("123");
//        user.setEmail("123");
//        user.setCreateTime(new Date());
//        user.setRole(1);
        String username = "admin";
        String password = "999";
        userService.login(username,password);

    }

}
