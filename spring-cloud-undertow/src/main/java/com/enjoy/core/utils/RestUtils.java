package com.enjoy.core.utils;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-14 19:17
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version v1.0
 * @description:
 * @author: 47
 */
//@Component
public class RestUtils {

    private static RestTemplate restTemplate;

    @Autowired
    private RestTemplate restTemplateValue;

    @PostConstruct
    public void init() {
        restTemplate = restTemplateValue;
    }

    public static String buildUrl(String hostAddress, String uri) {
        return hostAddress + uri;
    }

    public static String buildUrl(String scheme, String host, int port, String uri) {
        return scheme + "://" + host + ":" + port + uri;
    }

    public static <T> ResponseEntity<T> get(String uri, Map<String, String> headers, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Class<T> clazz) {
        return execute(uri, HttpMethod.GET, headers, uriVariable, queryVariable, null, clazz);
    }

    public static <T> ResponseEntity<T> get(String uri, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Class<T> clazz) {
        return execute(uri, HttpMethod.GET, null, uriVariable, queryVariable, null, clazz);
    }

    public static <T> ResponseEntity<T> get(String uri, String token, Map<String, Object> queryVariable, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.GET, headers, null, queryVariable, null, clazz);
    }

    public static <T> ResponseEntity<T> get(String uri, Map<String, Object> queryVariable, Class<T> clazz) {
        return execute(uri, HttpMethod.GET, null, null, queryVariable, null, clazz);
    }

    public static <T> ResponseEntity<T> get(String uri, String token, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.GET, headers, null, null, null, clazz);
    }

    public static <T> ResponseEntity<T> get(String uri, Class<T> clazz) {
        return execute(uri, HttpMethod.GET, null, null, null, null, clazz);
    }

    public static <T> ResponseEntity<T> post(String uri, Map<String, String> headers, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.POST, headers, uriVariable, queryVariable, body, clazz);
    }

    public static <T> ResponseEntity<T> post(String uri, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.POST, null, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> post(String uri, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.POST, null, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> post(String uri, String token, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.POST, headers, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> post(String uri, String token, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.POST, headers, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> put(String uri, Map<String, String> headers, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PUT, headers, uriVariable, queryVariable, body, clazz);
    }

    public static <T> ResponseEntity<T> put(String uri, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PUT, null, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> put(String uri, String token, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.PUT, headers, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> put(String uri, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PUT, null, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> put(String uri, String token, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.PUT, headers, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> patch(String uri, Map<String, String> headers, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PATCH, headers, uriVariable, queryVariable, body, clazz);
    }

    public static <T> ResponseEntity<T> patch(String uri, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PATCH, null, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> patch(String uri, String token, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.PATCH, headers, uriVariable, null, body, clazz);
    }

    public static <T> ResponseEntity<T> patch(String uri, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.PATCH, null, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> patch(String uri, String token, Object body, Class<T> clazz) {
        Map headers = new HashMap<String, String>(2);
        headers.put("X-Auth-Token", token);
        return execute(uri, HttpMethod.PATCH, headers, null, null, body, clazz);
    }

    public static <T> ResponseEntity<T> delete(String uri, Map<String, String> headers, Map<String, Object> uriVariable, Map<String, Object> queryVariable, Class<T> clazz) {
        return execute(uri, HttpMethod.DELETE, headers, uriVariable, queryVariable, null, clazz);
    }

    public static <T> ResponseEntity<T> delete(String uri, Map<String, Object> uriVariable, Class<T> clazz) {
        return execute(uri, HttpMethod.DELETE, null, uriVariable, null, null, clazz);
    }

    public static <T> ResponseEntity<T> delete(String uri, Map<String, Object> uriVariable, Object body, Class<T> clazz) {
        return execute(uri, HttpMethod.DELETE, null, uriVariable, null, body, clazz);
    }

    /**
     * HTTP/HTTPS请求
     *
     * @param uri           请求地址
     * @param httpMethod    请求方法
     * @param headers       请求头
     * @param uriVariables  路径参数
     * @param queryVariable 查询参数
     * @param body          请求体
     * @param clazz         返回值类型
     * @return
     */
    private static <T> ResponseEntity<T> execute(String uri, HttpMethod httpMethod, Map<String, String> headers,
                                                 Map<String, Object> uriVariables, Map<String, Object> queryVariable, Object body, Class<T> clazz) {
        HttpEntity httpEntity = new HttpEntity(body, buildHeader(headers));
        ResponseEntity<T> responseEntity = restTemplate.exchange(buildUri(uri, uriVariables, queryVariable), httpMethod, httpEntity, clazz);
        return responseEntity;
    }


    /**
     * 组装请求头
     *
     * @param headers
     * @return
     */
    private static HttpHeaders buildHeader(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Accept-Charset", "UTF-8");
        httpHeaders.add("Accept", "application/json; charset=UTF-8");
        //设置请求头
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }
        return httpHeaders;
    }

    /**
     * 组装查询参数
     *
     * @param uri
     * @param queryVariable
     * @return
     */
    private static URI buildUri(String uri, Map<String, Object> uriVariables, Map<String, Object> queryVariable) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
        if (Objects.nonNull(uriVariables)) {
            uriComponentsBuilder.uriVariables(uriVariables);
        }
        if (queryVariable != null) {
            for (Map.Entry<String, Object> entry : queryVariable.entrySet()) {
                uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return uriComponentsBuilder.build().encode().toUri();
    }
}
