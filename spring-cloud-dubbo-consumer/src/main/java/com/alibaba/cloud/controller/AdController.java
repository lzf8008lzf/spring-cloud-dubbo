package com.alibaba.cloud.controller;

import com.alibaba.cloud.dubbo.service.IDubboService;
import com.enjoy.cores.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("demo")
@Slf4j
public class AdController {

    @Resource(name = "consumerService")
    private IDubboService dubboConsumer;

    @RequestMapping(value = "sayHello")
    public Results sayHello() {
        return dubboConsumer.sayHello("Hello world!");
    }

    @RequestMapping(value = "welcomeAd")
    public Results welcomeAd() {
        return dubboConsumer.welcomeAd();
    }

    @RequestMapping(value = "bigData")
    public Results bigData() {
        return dubboConsumer.bigData();
    }

    @RequestMapping("exception")
    public Results exception()
    {
        return dubboConsumer.exception();
    }

    @RequestMapping("block")
    public Results block(Integer seconds)
    {
        return dubboConsumer.blockService(seconds);
    }

    @RequestMapping("lock")
    public Results lock()
    {
        return dubboConsumer.distributedLock();
    }

}
