package com.enjoy.controller;

import com.enjoy.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: hello-springboot
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-25 16:56
 **/


@RestController
public class ProducerController {

    @Autowired
    private SendService sendService;

    @RequestMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        sendService.sendMsg(msg);
    }


    @RequestMapping("/senddelay/{msg}")
    public void sendDelay(@PathVariable("msg") String msg) {
        sendService.sendDelayMsg(msg);
    }
}

