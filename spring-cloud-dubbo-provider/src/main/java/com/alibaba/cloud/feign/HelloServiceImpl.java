package com.alibaba.cloud.feign;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-18 14:46
 **/

public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
