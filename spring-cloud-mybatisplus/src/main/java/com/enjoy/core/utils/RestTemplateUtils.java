package com.enjoy.core.utils;

import com.enjoy.core.framework.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @program: spring.cloud.distributed.lock
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-05 14:37
 **/

public class RestTemplateUtils {
    private static Logger log = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static final RestTemplate restTemplate = SpringContextHolder.getBean("restTemplate");

    /**
     *
     * @param url
     * @return String
     */
    public static String httpGet(String url){
        return httpGet(url,null);
    }

    /**
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static String httpGet(String url,  Map<String, String> headerMap){

        HttpHeaders headers = new HttpHeaders();
        headerMap.forEach((k,v)->{headers.add(k,v);});

        HttpEntity requestEntity = new HttpEntity(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);;
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param url
     * @return String
     */
    public static String httpPost(String url){
        return httpPost(url,null,null);
    }

    public static String httpPost(String url, Map<String, Object> paramMap){
        return httpPost(url,paramMap,null);
    }

    /**
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static String httpPost(String url, Map<String, Object> paramMap, Map<String, String> headerMap){

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        paramMap.forEach((k,v)->{param.add(k,v);});

        HttpHeaders headers = new HttpHeaders();
        headerMap.forEach((k,v)->{headers.add(k,v);});

        HttpEntity<MultiValueMap<String,Object>> requestEntity = new HttpEntity<>(param, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url,requestEntity,String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
