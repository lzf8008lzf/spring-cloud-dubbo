package com.enjoy.core.utils;


import com.enjoy.core.result.Results;
import com.enjoy.core.result.ResultsUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program: service-monitor
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-11 14:39
 **/

public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setSocketTimeout(3000)
            .setConnectTimeout(3000)
            .build();

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();

        httpClientConnectionManager.setMaxTotal(200);

        httpClientConnectionManager.setDefaultMaxPerRoute(20);

        httpClientConnectionManager.setDefaultMaxPerRoute(50);

        httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
    }

    /*
     *解析请求包体
     */
    public static String parseRequestBody(HttpServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        try {
            // 读取请求内容
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     *
     * @param url
     * @return String
     */
    public static Results httpGet(String url){
        return httpGet(url,null,null);
    }

    /**
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static Results httpGet(String url,Map<String, String> headerMap){
        return httpGet(url,null,headerMap);
    }

    /**
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static Results httpGet(String url, Map<String, String> paramMap, Map<String, String> headerMap){

        Results results = ResultsUtil.ok();

        CloseableHttpResponse response = null;

        try {

            URIBuilder uriBuilder = new URIBuilder(url);

            //请求参数
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> list = new LinkedList<>();

                paramMap.forEach((key, value) -> {
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                });

                uriBuilder.setParameters(list);
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setConfig(REQUEST_CONFIG);

            //请求头参数
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((key, value) -> {
                    httpGet.addHeader(key,value);
                });
            }

            //设置请求状态参数
            response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();//获取返回状态值
            if (status == HttpStatus.SC_OK) {//请求成功
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null){
                    String resStr = EntityUtils.toString(httpEntity, "UTF-8");

                    results.setData(resStr);
                    EntityUtils.consume(httpEntity);//关闭资源
                }
            }else {
                results.setError(""+status,response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            results.setError("800",e.getMessage());
        } finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;

    }

    /**
     *
     * @param url
     * @return String
     */
    public static Results httpPost(String url){
        return httpPost(url,null,null);
    }

    public static Results httpPost(String url, Map<String, String> paramMap){
        return httpPost(url,paramMap,null);
    }

    /**
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static Results httpPost(String url, Map<String, String> paramMap, Map<String, String> headerMap){

        Results results = ResultsUtil.ok();

        CloseableHttpResponse response = null;

        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(REQUEST_CONFIG);

            //请求头参数
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((key, value) -> {
                    httpPost.addHeader(key,value);
                });
            }

            //请求参数
            if (MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> list = new LinkedList<>();

                paramMap.forEach((key, value) -> {
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    list.add(basicNameValuePair);
                });

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
                httpPost.setEntity(entity);
            }

            response = httpClient.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();//获取返回状态值
            if (status == HttpStatus.SC_OK) {//请求成功
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null){
                    String resStr = EntityUtils.toString(httpEntity, "UTF-8");

                    results.setData(resStr);
                    EntityUtils.consume(httpEntity);//关闭资源
                }
            }else {
                results.setError(""+status,response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            results.setError("800",e.getMessage());
        } finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;

    }

    /**
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static Results httpPostJson(String url,String jsonStr){
        return httpPostJson(url,jsonStr,null);
    }

    /**
     *
     * @param url
     * @param jsonStr
     * @param headerMap
     * @return
     */
    public static Results httpPostJson(String url,String jsonStr,Map<String, String> headerMap){

        Results results = ResultsUtil.ok();

        CloseableHttpResponse response = null;

        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(REQUEST_CONFIG);

            //请求头参数
            if (MapUtils.isNotEmpty(headerMap)) {
                headerMap.forEach((key, value) -> {
                    httpPost.addHeader(key,value);
                });
            }

            StringEntity stringEntity = new StringEntity(jsonStr,Consts.UTF_8);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);

            response = httpClient.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();//获取返回状态值
            if (status == HttpStatus.SC_OK) {//请求成功
                HttpEntity httpEntity = response.getEntity();
                if(httpEntity != null){
                    String resStr = EntityUtils.toString(httpEntity, "UTF-8");

                    results.setData(resStr);
                    EntityUtils.consume(httpEntity);//关闭资源
                }
            }else {
                results.setError(""+status,response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            results.setError("800",e.getMessage());
        } finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;

    }
}

