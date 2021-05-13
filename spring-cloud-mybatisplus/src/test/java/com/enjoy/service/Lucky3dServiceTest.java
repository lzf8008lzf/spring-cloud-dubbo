package com.enjoy.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.enjoy.MybatisplusApplication;
import com.enjoy.core.utils.RestTemplateUtils;
import com.enjoy.core.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-18 14:50
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class Lucky3dServiceTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("redisTemplateLocal")
    private StringRedisTemplate redisTemplateLocal;

    @Test
    public void redisTest(){
        stringRedisTemplate.opsForValue().set("yuexinag","悦享商城");
        redisTemplateLocal.opsForValue().set("mall","商城redis");
    }

    public static void main(String[] args) {

        long timestamp = DateUtil.current();
        String noncestr = RandomUtil.randomString(16);

        Map<String, Object> param = new HashMap<>(3);
        param.put("uid", 9527);
        param.put("time_expire", 3000);

//        param.put("imei", reqParam.get("imei"));
//        param.put("idfa", reqParam.get("idfa"));
//        param.put("system", reqParam.get("clientType"));
//        param.put("channel", reqParam.get("channel"));
//        param.put("device", reqParam.get("phoneBrand"));
//        param.put("osversion", reqParam.get("osVersion"));
//        param.put("version", reqParam.get("appVersion"));
//        param.put("pushid", reqParam.get("pushId"));
//        param.put("resolution", reqParam.get("resolution"));
//        param.put("lat", reqParam.get(""));
//        param.put("lng", reqParam.get(""));
//        param.put("ip", "");

        Map<String, String> headers = new HashMap<>(4);
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept-Charset", "UTF-8");
        headers.put("X-Ca-Front-AccessKey", "yx9d49dxea9z2r7s75");
        headers.put("X-Ca-Front-Timestamp", timestamp+"");
        headers.put("X-Ca-Front-Noncestr", noncestr);

        String sign = SignUtil.createSign(param,"28609A84B94C49DFA0DC1F85DD578A19",timestamp+noncestr);
        headers.put("X-Ca-Front-Signature", sign);

        String response = RestTemplateUtils.httpPostHeader("http://test.openapi.yuexiangvideo.com/api/token/set?uid=9527&time_expire=3000",headers);

        System.out.println(response);
//        System.out.println(NumberUtil.isPrime(13));
//
//        System.out.println(NumberUtil.isOdd(35));
//
//        System.out.println(NumberUtil.format3D(999));
//
//        System.out.println(NumberUtil.sum(789));
//
//        System.out.println(NumberUtil.oddEven("789"));
    }

}
