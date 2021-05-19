package com.enjoy.config;


import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-14 19:23
 **/

@Configuration
@Component
@ConfigurationProperties(prefix = "http")
@Setter
public class RestHttpClientConfig {
    //   最大连接数
    private Integer maxtotal;
    //   并发访问数
    private Integer defaultMaxPerRoute;
    //   连接超时
    private Integer connectTimeout;
    //   请求超时
    private Integer connectionRequestTimeout;
    //   socket超时
    private Integer socketTimeout;

    //  1、配置一个连接管理器
    @Bean
    public PoolingHttpClientConnectionManager connectionManager(){
        //HttpClient链接管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        connectionManager.setMaxTotal(maxtotal);
        //设置并发访问数
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return connectionManager;
    }
    //   2、配置请求
    @Bean
    public RequestConfig requestConfig(){
        //设置请求配置RequestConfig
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接超时
                .setConnectTimeout(connectTimeout)
                //设置请求超时
                .setConnectionRequestTimeout(connectionRequestTimeout)
                // 设置Socket超时
                .setSocketTimeout(socketTimeout)
                .build();
        return requestConfig;
    }

    //3、设置httpClient
    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager connectionManager, RequestConfig requestConfig){
        //    创建客户端
        CloseableHttpClient httpClient = HttpClients
                .custom()
                //设置连接器
                .setConnectionManager(connectionManager)
                //设置请求配置
                .setDefaultRequestConfig(requestConfig)
                .build();
        return httpClient;
    }


    //    4、构造一个工厂
    @Bean
    public ClientHttpRequestFactory requestFactory(HttpClient httpClient){
        //工厂调用HttpClient
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    //    5、设置RestTemplate
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory){
        //    RestTemplate 整合HttpClient ,需要请求工厂
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        //乱码处理
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> mc : list) {
            if (mc instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) mc).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
        return restTemplate;
    }
}
