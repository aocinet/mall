package com.mmall.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class FilterTest {
    @Autowired
    IUserService userService;
    @Test
    public void testFilter() {
        String username = "admin";
        String password = "999";
        userService.login(username, password);
//        userService.getInformation(user.getId())
    }
}
