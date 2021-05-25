package com.enjoy.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.enjoy.MybatisplusApplication;
import com.enjoy.core.utils.RestTemplateUtils;
import com.enjoy.core.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-25 16:10
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class MiniServiceTest {

    public static void main(String[] args) {

        String url="http://api.wecar.map.qq.com/account/mini/code2session";

        Map<String, String> headers = new HashMap<>(4);
        headers.put("Content-Type", "application/json");
        headers.put("Accept-Charset", "UTF-8");

        Map<String, Object> param = new HashMap<>(3);
        param.put("appid", "60427");
        param.put("timestamp", DateUtil.currentSeconds());
        param.put("nonce", RandomUtil.randomString(9));
        param.put("code", "29225ba02ee811eba252b55ce8cf45c8");
        param.put("version", "1.0");
        param.put("grant_type", "authorization_code");
        String sign = SignUtil.createMiniSign(param,"C7116341F238E61AD678A1797AFEEB7F");
        param.put("sign", sign);

        String response = null;
        try {
            response = RestTemplateUtils.httpPost(url,param,headers);

            System.out.println(response);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
