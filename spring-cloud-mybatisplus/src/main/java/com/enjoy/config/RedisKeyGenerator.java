package com.enjoy.config;


import com.enjoy.core.framework.SpringContextHolder;
import com.enjoy.core.utils.LongRedisTemplate;
import lombok.Generated;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Objects;
import java.util.Properties;

/**
 * @program: basic-server
 * @description:
 * @author: LiZhaofu
 * @create: 2020-11-16 14:17
 **/

public class RedisKeyGenerator implements ShardingKeyGenerator {

    public static final String REDIS_COMMENTID_KEY="cache:ID:cms_comment";

    private Properties properties = new Properties();

    private LongRedisTemplate longRedisTemplate = SpringContextHolder.getBean("longRedisTemplate");

    @Override
    public Comparable<?> generateKey() {
        Long id = longRedisTemplate.opsForValue().get(REDIS_COMMENTID_KEY);

        if(Objects.isNull(id)){
            id = Long.parseLong(properties.getProperty("commentid","7879088"));
            longRedisTemplate.opsForValue().set(REDIS_COMMENTID_KEY,id);
        }else{
            longRedisTemplate.opsForValue().increment(REDIS_COMMENTID_KEY);
        }
        return id;
    }

    @Override
    public String getType() {
        return "RedisKeyGenerator";
    }

    @Generated
    public Properties getProperties() {
        return this.properties;
    }

    @Generated
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
