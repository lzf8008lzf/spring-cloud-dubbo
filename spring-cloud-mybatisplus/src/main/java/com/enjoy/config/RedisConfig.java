package com.enjoy.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-13 14:48
 **/

@Configuration
@Slf4j
public class RedisConfig {

    private org.redisson.config.Config loadConfig(ClassLoader classLoader, String fileName) {
        InputStream is = classLoader.getResourceAsStream(fileName);
        if (is != null) {
            try {
                return org.redisson.config.Config.fromYAML(is);
            } catch (IOException e) {
                try {
                    is = classLoader.getResourceAsStream(fileName);
                    return org.redisson.config.Config.fromJSON(is);
                } catch (IOException e1) {
                    log.error("Can't parse yaml config", e1);
                }
            }
        }
        return null;
    }

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient = null;

        Config config = loadConfig(RedisConfig.class.getClassLoader(), "config/redisson.yaml");;
        redissonClient = Redisson.create(config);


        return redissonClient;
    }

}
