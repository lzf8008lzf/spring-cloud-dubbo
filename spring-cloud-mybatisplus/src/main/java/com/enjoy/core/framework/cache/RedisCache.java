package com.enjoy.core.framework.cache;

/**
 * @program: spring-cloud-dubbo
 * @description: Redis缓存工具类
 * @author: LiZhaofu
 * @create: 2020-06-08 10:40
 **/

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class RedisCache implements Cache //实现类
{

    private static RedisTemplate<String,Object> redisTemplate;

    private final String id;

    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ReadWriteLock getReadWriteLock()
    {
        return this.readWriteLock;
    }

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        log.debug("MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            log.debug(">>>>>>putObject: key="+key+",value="+value);
            if(null!=value)
                redisTemplate.opsForValue().set(key.toString(),value,60, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis保存数据异常！");
        }
    }

    @Override
    public Object getObject(Object key) {
        try{
            log.debug(">>>>>>getObject: key="+key);
            if(null!=key)
                return redisTemplate.opsForValue().get(key.toString());
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try{
            if(null!=key)
                return redisTemplate.expire(key.toString(),1,TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public void clear() {
        Long size=redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Long size = redisConnection.dbSize();
                //连接清除数据
                redisConnection.flushDb();
                redisConnection.flushAll();
                return size;
            }
        });
        log.info(">>>>>>clear: 清除了" + size + "个对象");
    }

    @Override
    public int getSize() {
        Long size = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }
}