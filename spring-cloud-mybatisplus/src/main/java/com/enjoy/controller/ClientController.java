package com.enjoy.controller;

import com.alibaba.fastjson.JSON;
import com.enjoy.core.result.AjaxResult;
import com.enjoy.core.utils.RestTemplateUtils;
import com.enjoy.core.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端
 *
 * @author : SPL
 * @since : 2020-04-29 11:31
 **/
@RequestMapping("/client")
@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/send")
    public Object send() {

        Map<String, Object> param = new HashMap<>(3);
        param.put("userId", 9527);
        param.put("amount", "9.99");
        param.put("productId", "9885544154");


        Map<String, String> headers = new HashMap<>(4);
        headers.put("appid", "9527");
        headers.put("timestamp", String.valueOf(new Date().getTime()));
        headers.put("nonce", "9885544154");
        String sign = SignUtil.createSign(headers,"mysecret123456");
        headers.put("sign", sign);

        String retStr = RestTemplateUtils.httpPost("http://127.0.0.1:8100/server/test",param,headers);
        AjaxResult jsonRes = JSON.parseObject(retStr, AjaxResult.class);

        return jsonRes;
    }

}
