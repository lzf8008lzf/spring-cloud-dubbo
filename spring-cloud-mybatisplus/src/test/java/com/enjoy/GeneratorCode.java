package com.enjoy;

import cn.hutool.json.JSONUtil;
import com.enjoy.core.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-06-01 14:16
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GeneratorCode {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> requestEntity = new HttpEntity<String>("",  headers);

//        String response = restTemplate.postForObject("http://www.yuexiang365.cn/api/V3.8/userList?ids=1,7",requestEntity,String.class);
//
//        Results results = JSONUtil.toBean(response,Results.class);
//        log.info(JSONUtil.toJsonStr(results));

        Map map = new HashMap<>(1);
        if ("".equals(map.get("id"))|| Objects.isNull(map.get("id"))){
            System.err.println("-------");
        }else {
            System.out.println(map.get("id"));
        }
    }
}
