package com.feign.client;


import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;

//import okhttp3.ConnectionPool;
//import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;


@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignConfiguration implements RequestInterceptor {

//    @Bean
//    public OkHttpClient okHttpClient() {
//        return new OkHttpClient.Builder()
//                // 连接超时
//                .connectTimeout(20, TimeUnit.SECONDS)
//                // 响应超时
//                .readTimeout(20, TimeUnit.SECONDS)
//                // 写超时
//                .writeTimeout(20, TimeUnit.SECONDS)
//                // 是否自动重连
//                .retryOnConnectionFailure(true)
//                // 连接池
//                .connectionPool(new ConnectionPool())
//                .build();
//    }


    @Bean
    @Primary
    public feign.Logger.Level multipartLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public feign.Logger logger() {
        return new FeignLogger();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //这里最后向requestTemplate里面放入一个是否为feign调用的标记
        requestTemplate.header("isFeign","true");
    }

}


