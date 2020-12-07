package com.enjoy.core.utils;

import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class LongRedisTemplate extends RedisTemplate<String, Long> {
    GenericToStringSerializer<Long> genericToStringSerializer = new GenericToStringSerializer<>(Long.class);

    public LongRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(genericToStringSerializer);
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(genericToStringSerializer);
    }

    public LongRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
