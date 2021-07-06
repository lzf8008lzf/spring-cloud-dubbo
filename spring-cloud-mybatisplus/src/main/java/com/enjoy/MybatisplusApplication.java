package com.enjoy;

import com.enjoy.core.framework.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching //Spring Cache Redis
public class MybatisplusApplication {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
    }

}

