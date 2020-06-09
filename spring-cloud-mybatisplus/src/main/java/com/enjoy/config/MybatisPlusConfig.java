package com.enjoy.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.enjoy.core.framework.cache.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @program: spring-cloud-dubbo
 * @description: Mybatis-plus配置类
 * @author: LiZhaofu
 * @create: 2020-05-30 16:45
 **/

@Configuration
@MapperScan("com.enjoy.mapper")
@Slf4j
public class MybatisPlusConfig {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache.setRedisTemplate(redisTemplate);
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    public PerformanceMonitorInterceptor performanceInterceptor() {
        return new PerformanceMonitorInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager transactionManager(){
//        log.info("初始化transactionManager");
//        return new DataSourceTransactionManager(dataSource);
//    }
}

