package com.enjoy.controller;

import com.enjoy.core.annotation.Signature;
import com.enjoy.core.result.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 服务端
 *
 * @author : SPL
 * @since : 2020-04-29 11:31
 **/
@RequestMapping("/server")
@RestController
public class ServerController {

    @PostMapping("/test")
    @Signature
    public Object test(String userId, BigDecimal amount, String productId){

        //业务处理
        System.out.println("-------------  " + userId);
        System.out.println("-------------  " + amount);
        System.out.println("-------------  " + productId);

        return AjaxResult.success("请求成功");
    }

}
