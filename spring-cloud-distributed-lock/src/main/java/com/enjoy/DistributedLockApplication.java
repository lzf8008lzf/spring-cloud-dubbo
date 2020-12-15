package com.enjoy;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.cache.CacheException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class DistributedLockApplication {

    private Config loadConfig(ClassLoader classLoader, String fileName) {
        InputStream is = classLoader.getResourceAsStream(fileName);
        if (is != null) {
            try {
                return Config.fromYAML(is);
            } catch (IOException e) {
                try {
                    is = classLoader.getResourceAsStream(fileName);
                    return Config.fromJSON(is);
                } catch (IOException e1) {
                    throw new CacheException("Can't parse yaml config", e1);
                }
            }
        }
        return null;
    }

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient = null;

        Config config = loadConfig(DistributedLockApplication.class.getClassLoader(), "redisson.yaml");;
        redissonClient = Redisson.create(config);

        return redissonClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApplication.class, args);
    }
}

