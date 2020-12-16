package com.enjoy.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-15 16:11
 **/

@RestController
@RequestMapping("demo")
@Slf4j
public class DemoController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "sayHello")
    public String sayHello() throws Exception{

        RLock lock = redissonClient.getLock("rlock");
        // 为加锁等待3秒时间，并在加锁成功12秒钟后自动解开
        boolean res = lock.tryLock(3, 12, TimeUnit.SECONDS);
        if (res) {
            log.info("获取锁成功！");
        }else {
            log.error("获取锁失败");
        }
        try {
            System.out.println(lock);
            Thread.sleep(11000);
        } finally {
            lock.unlock();
        }

        return "Hello world!";
    }
}
