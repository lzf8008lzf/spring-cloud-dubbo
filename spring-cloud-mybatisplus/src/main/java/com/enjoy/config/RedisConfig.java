package com.enjoy.config;

import com.enjoy.core.utils.LongRedisTemplate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-13 14:48
 **/

@Configuration
@Slf4j
@EnableConfigurationProperties(RedisProperties.class)
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

    @Bean
    public StringRedisTemplate redisTemplateLocal(RedisProperties redisProperties) {
        RedisConnectionFactory redisConnectionFactory=createLettuceConnectionFactory(redisProperties);
        return createRedisTemplate(redisConnectionFactory);
    }

    /**
     * 自定义LettuceConnectionFactory,这一步的作用就是返回根据你传入参数而配置的
     * LettuceConnectionFactory，
     * 也可以说是LettuceConnectionFactory的原理了，
     * 后面我会详细讲解的,各位同学也可先自己看看源码

     这里定义的方法 createLettuceConnectionFactory，方便快速使用
     */
    private LettuceConnectionFactory createLettuceConnectionFactory(RedisProperties redisProperties){

        //redis配置
        RedisConfiguration redisConfiguration = new
                RedisStandaloneConfiguration(redisProperties.getHost(),redisProperties.getPort());
        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(5);
        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(redisProperties.getPassword());

        //连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig =
                new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());

        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration.builder().
                commandTimeout(redisProperties.getTimeout());

        builder.shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout());
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
        lettuceConnectionFactory .afterPropertiesSet();

        return lettuceConnectionFactory;
    }

    /**
     * json 实现 redisTemplate
     * <p>
     * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
     *
     * @param redisConnectionFactory
     * @return
     */
    public StringRedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
