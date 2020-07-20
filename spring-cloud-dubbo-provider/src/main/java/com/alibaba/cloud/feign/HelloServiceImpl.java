package com.alibaba.cloud.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-18 14:46
 **/
//@RestController
public class HelloServiceImpl implements HelloService{

    public HelloServiceImpl()
    {
        System.err.println("=========================================");
    }
    @Override
    @GetMapping("/hello")
    public String hello(String name) {
        return "hello " + name;
    }
}
