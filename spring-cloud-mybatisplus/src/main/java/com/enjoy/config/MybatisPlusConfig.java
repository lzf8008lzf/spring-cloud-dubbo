package com.enjoy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-cloud-dubbo
 * @description: Mybatis-plus配置类
 * @author: LiZhaofu
 * @create: 2020-05-30 16:45
 **/

@Configuration
@MapperScan("com.fendo.mybatis.plus.mapper*")
public class MybatisPlusConfig {
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

