package com.enjoy.config;

import com.enjoy.core.handler.VisitLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-17 14:43
 **/

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private VisitLimitInterceptor visitLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitLimitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/v2/api-docs");
    }
}
