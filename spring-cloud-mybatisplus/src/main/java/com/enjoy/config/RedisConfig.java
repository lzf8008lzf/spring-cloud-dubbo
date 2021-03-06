package com.enjoy.config;

import cn.hutool.core.util.StrUtil;
import com.enjoy.config.redisson.SingleServerProperties;
import com.enjoy.core.framework.cache.RedissonCache;
import com.enjoy.core.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

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

    @Bean(destroyMethod="shutdown")
    public RedissonClient redissonClient(SingleServerProperties properties) throws IOException {
        String jsonString = JacksonUtil.toJson(properties);
        Config config = Config.fromYAML(jsonString);
        if (StrUtil.isEmpty(properties.getSingleServerConfig().getPassword())){
            config.useSingleServer().setPassword(null);
        }
        return Redisson.create(config);
    }

    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean(value = "redisTemplateLocal")
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

        /* ========= 基本配置 ========= */
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());
        configuration.setDatabase(5);
//        if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
            configuration.setPassword(redisProperties.getPassword());
//        }

        //连接池配置
        GenericObjectPoolConfig genericObjectPoolConfig =
                new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
//        genericObjectPoolConfig.setNumTestsPerEvictionRun((int)redisProperties.getJedis().getPool().getTimeBetweenEvictionRuns().get(ChronoUnit.SECONDS));

        /* ========= jedis pool ========= */
        /*
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        builder.connectTimeout(redisProperties.getTimeout());
        builder.usePooling();
        builder.poolConfig(genericObjectPoolConfig);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration, builder.build());
        // 连接池初始化
        connectionFactory.afterPropertiesSet();
        */

        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(redisProperties.getTimeout());
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    /**
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
