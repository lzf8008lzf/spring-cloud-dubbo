package com.enjoy.core.framework.cache;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-04 15:08
 **/

import com.enjoy.core.framework.SpringContextHolder;
import com.enjoy.core.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.cache.Cache;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * MyBatis二级缓存Redis实现
 * 重点处理以下几个问题
 * 1、缓存穿透：存储空值解决，MyBatis框架实现
 * 2、缓存击穿：使用互斥锁，我们自己实现
 * 3、缓存雪崩：缓存有效期设置为一个随机范围，我们自己实现
 * 4、读写性能：redis key不能过长，会影响性能，这里使用SHA-256计算摘要当成key
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    /**
     * 统一字符集
     */
    private static final String CHARSET = "utf-8";
    /**
     * key摘要算法
     */
    private static final String ALGORITHM = "SHA-256";
    /**
     * 统一缓存头
     */
    private static final String CACHE_NAME = "MyBatis:";
    /**
     * 读写锁：解决缓存击穿
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    /**
     * 表空间ID：方便后面的缓存清理
     */
    private final String id;
    /**
     * redis服务接口：提供基本的读写和清理
     */
    private static volatile RedisService redisService;
    /**
     * 信息摘要
     */
    private volatile MessageDigest messageDigest;

    /////////////////////// 解决缓存雪崩，具体范围根据业务需要设置合理值 //////////////////////////
    /**
     * 缓存最小有效期
     */
    private static final int MIN_EXPIRE_MINUTES = 60;
    /**
     * 缓存最大有效期
     */
    private static final int MAX_EXPIRE_MINUTES = 120;

    /**
     * MyBatis给每个表空间初始化的时候要用到
     * @param id 其实就是namespace的值
     */
    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    /**
     * 获取ID
     * @return 真实值
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 创建缓存
     * @param key 其实就是sql语句
     * @param value sql语句查询结果
     */
    @Override
    public void putObject(Object key, Object value) {
        try {
            String strKey = getKey(key);
            // 有效期为1~2小时之间随机，防止雪崩
            int expireMinutes = RandomUtils.nextInt(MIN_EXPIRE_MINUTES, MAX_EXPIRE_MINUTES);
            getRedisService().set(strKey, value, expireMinutes, TimeUnit.MINUTES);
            log.debug("Put cache to redis, id={}", id);
        } catch (Exception e) {
            log.error("Redis put failed, id=" + id, e);
        }
    }

    /**
     * 读取缓存
     * @param key 其实就是sql语句
     * @return 缓存结果
     */
    @Override
    public Object getObject(Object key) {
        try {
            String strKey = getKey(key);
            log.debug("Get cache from redis, id={}", id);
            return getRedisService().get(strKey);
        } catch (Exception e) {
            log.error("Redis get failed, fail over to db", e);
            return null;
        }
    }

    /**
     * 删除缓存
     * @param key 其实就是sql语句
     * @return 结果
     */
    @Override
    public Object removeObject(Object key) {
        try {
            String strKey = getKey(key);
            getRedisService().delete(strKey);
            log.debug("Remove cache from redis, id={}", id);
        } catch (Exception e) {
            log.error("Redis remove failed", e);
        }
        return null;
    }

    /**
     * 缓存清理
     * 网上好多博客这里用了flushDb甚至是flushAll，感觉好坑鸭！
     * 应该是根据表空间进行清理
     */
    @Override
    public void clear() {
        try {
            log.debug("clear cache, id={}", id);
            String hsKey = CACHE_NAME + id;
            // 获取CacheNamespace所有缓存key
            Map<Object, Object> idMap = getRedisService().hashEntries(hsKey);
            if (!idMap.isEmpty()) {
                Set<Object> keySet = idMap.keySet();
                Set<String> keys = new HashSet<>(keySet.size());
                keySet.forEach(item -> keys.add(item.toString()));
                // 清空CacheNamespace所有缓存
                getRedisService().delete(keys);
                // 清空CacheNamespace
                getRedisService().delete(hsKey);
            }
        } catch (Exception e) {
            log.error("clear cache failed", e);
        }
    }

    /**
     * 获取缓存大小，暂时没用上
     * @return 长度
     */
    @Override
    public int getSize() {
        return 0;
    }

    /**
     * 获取读写锁：为了解决缓存击穿
     * @return 锁
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    /**
     * 计算出key的摘要
     * @param cacheKey CacheKey
     * @return 字符串key
     */
    private String getKey(Object cacheKey) {
        String cacheKeyStr = cacheKey.toString();
        log.debug("count hash key, cache key origin string:{}", cacheKeyStr);
        String strKey = byte2hex(getSHADigest(cacheKeyStr));
        log.debug("hash key:{}", strKey);
        String key = CACHE_NAME + strKey;
        // 在redis额外维护CacheNamespace创建的key，clear的时候只清理当前CacheNamespace的数据
        getRedisService().hashSet(CACHE_NAME + id, key, "1");
        return key;
    }

    /**
     * 获取信息摘要
     * @param data 待计算字符串
     * @return 字节数组
     */
    private byte[] getSHADigest(String data) {
        try {
            if (messageDigest == null) {
                synchronized (MessageDigest.class) {
                    if (messageDigest == null) {
                        messageDigest = MessageDigest.getInstance(ALGORITHM);
                    }
                }
            }
            return messageDigest.digest(data.getBytes(CHARSET));
        } catch (Exception e) {
            log.error("SHA-256 digest error: ", e);
        }

        return null;
    }

    /**
     * 字节数组转16进制字符串
     * @param bytes 待转换数组
     * @return 16进制字符串
     */
    private String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 获取Redis服务接口
     * 使用双重检查保证线程安全
     * @return 服务实例
     */
    private RedisService getRedisService() {
        if (redisService == null) {
            synchronized (RedisService.class) {
                if (redisService == null) {
                    redisService = SpringContextHolder.getBean(RedisService.class);
                }
            }
        }
        return redisService;
    }
}
