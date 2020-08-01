package com.alibaba.cloud.controller;

import com.alibaba.cloud.consumer.DubboConsumer;
import com.alibaba.cloud.dubbo.WelcomeAd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V3.6")
@Slf4j
public class AdController {

    @Autowired
    private DubboConsumer dubboConsumer;

    @RequestMapping(value = "welcomeAd", method = { RequestMethod.POST })
    public WelcomeAd welcomeAd() {
        return dubboConsumer.welcomeAd();
    }

}
