package com.enjoy.controller;

import com.alibaba.fastjson.JSON;
import com.enjoy.core.result.AjaxResult;
import com.enjoy.core.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("userId", "9527");
        param.add("amount", "9.99");
        param.add("productId", "9885544154");

        HttpHeaders headers = new HttpHeaders();

        headers.add("appid", "9527");
        headers.add("timestamp", String.valueOf(new Date().getTime()));
        headers.add("nonce", "9885544154");
        String sign = SignUtil.createSign(headers.toSingleValueMap(),"mysecret123456");
        headers.add("sign", sign);

        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(param, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8100/server/test",requestEntity,String.class);
            System.out.println(response.getBody());
            AjaxResult jsonRes = JSON.parseObject(response.getBody(), AjaxResult.class);
            return jsonRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
