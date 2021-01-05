package com.enjoy.controller;

import com.enjoy.core.annotation.ApiSignature;
import com.enjoy.core.result.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    @ApiSignature
    public Object test(String userId, BigDecimal amount, String productId){

        //业务处理
        Map<String, Object> data = new HashMap<>(3);

        data.put("userId", userId);
        data.put("amount", amount);
        data.put("productId", productId);

        return AjaxResult.success("请求成功",data);
    }

}
