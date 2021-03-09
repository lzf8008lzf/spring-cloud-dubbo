package com.enjoy.service;

import com.enjoy.MybatisplusApplication;
import com.enjoy.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-03-09 10:37
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        User user = userService.saveOrUpdate(new User(1L, "张三", 21));
        log.info("[saveOrUpdate] - [{}]", user);
        final User user1 = userService.get(1L);
        log.info("[get] - [{}]", user1);
    }
}
