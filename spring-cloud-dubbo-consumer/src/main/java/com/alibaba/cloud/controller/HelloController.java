package com.alibaba.cloud.controller;

import com.alibaba.cloud.feign.HelloServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-18 15:01
 **/

//@RestController
public class HelloController {
    @Autowired
    private HelloServiceClient helloServiceClient;

    HelloController()
    {
        System.err.println("----------------------");
    }

    @GetMapping("/test")
    public String test(String name) {
        return helloServiceClient.hello(name);
    }
}
