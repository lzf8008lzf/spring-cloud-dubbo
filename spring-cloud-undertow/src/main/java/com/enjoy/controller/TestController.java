package com.enjoy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-08 11:35
 **/

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "success !";
    }
}
