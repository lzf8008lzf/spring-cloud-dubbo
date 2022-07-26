package com.feign.client;


import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class FeignConfiguration implements RequestInterceptor {

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


