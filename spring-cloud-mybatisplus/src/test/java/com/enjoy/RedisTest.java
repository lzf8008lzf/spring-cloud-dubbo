package com.enjoy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-05-18 18:20
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void delete(){

        String pattern ="*allUserLookVideoTagsRankForDay";
        Set<String> keys =stringRedisTemplate.keys(pattern);
        log.info(keys.size()+"");

        stringRedisTemplate.delete(keys);
        keys =stringRedisTemplate.keys(pattern);
        log.info(keys.size()+"");

        stringRedisTemplate.execute((RedisConnection redisConnection) -> {
            System.out.println(redisConnection);
//            redisConnection.publish()
            redisConnection.set("key".getBytes(), "value".getBytes());
            redisConnection.set("key1".getBytes(), "value1".getBytes());
            return null;
        });

        stringRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("key", "value");
                redisOperations.opsForValue().set("key1", "value1");
                return null;
            }
        });

        //使用 pipeline
//        stringRedisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
//            redisConnection.hSet("hell1".getBytes(), "hash1".getBytes(), "hellovalue1".getBytes());
//            redisConnection.hSet("hell2".getBytes(), "hash2".getBytes(), "hellovalue2".getBytes());
//            redisConnection.hSet("hell3".getBytes(), "hash3".getBytes(), "hellovalue3".getBytes());
//            redisConnection.hSet("hell4".getBytes(), "hash4".getBytes(), "hellovalue4".getBytes());
//            return null;
//        });
    }
}
