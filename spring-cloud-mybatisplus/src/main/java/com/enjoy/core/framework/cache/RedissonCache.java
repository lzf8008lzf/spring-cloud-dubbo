/**
 * Copyright (c) 2013-2020 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.enjoy.core.framework.cache;

import com.enjoy.config.MybatisPlusConfig;
import com.enjoy.core.utils.CodecUtil;
import com.enjoy.core.utils.JacksonUtil;
import org.apache.ibatis.cache.Cache;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * MyBatis cache implementation
 *
 *
 */
public class RedissonCache implements Cache {

    private static Logger log = LoggerFactory.getLogger(RedissonCache.class);

    private String id;
    private RMapCache<Object, Object> mapCache;
    private long timeToLive;
    private long maxIdleTime;
    private int maxSize;
    private String redissonConfig;

    public RedissonCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        if(Objects.isNull(o)||Objects.isNull(o1)){
            return ;
        }
        check();
        String key = CodecUtil.getKey(o);
        mapCache.put(key, o1, timeToLive, TimeUnit.MILLISECONDS, maxIdleTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public Object getObject(Object o) {
        check();
        String key = CodecUtil.getKey(o);
        return mapCache.get(key);
    }

    @Override
    public Object removeObject(Object o) {
        check();
        String key = CodecUtil.getKey(o);
        return mapCache.remove(key);
    }

    @Override
    public void clear() {
        check();
        mapCache.clear();
    }

    @Override
    public int getSize() {
        check();
        return mapCache.size();
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setMaxIdleTime(long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    public String getRedissonConfig() {
        return redissonConfig;
    }

    public void setRedissonConfig(String redissonConfig) {
        Config cfg;
        try {
            String jsonConfig = JacksonUtil.toJson(MybatisPlusConfig.singleServerProperties);
            log.info(jsonConfig);
            cfg = Config.fromYAML(jsonConfig);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't parse config", e);
        }

        RedissonClient redisson = Redisson.create(cfg);
        mapCache = getMapCache(id, redisson);
        if (maxSize > 0) {
            mapCache.setMaxSize(maxSize);
        }
    }

    protected RMapCache<Object, Object> getMapCache(String id, RedissonClient redisson) {
        return redisson.getMapCache(id);
    }

    private void check() {
        if (mapCache == null) {
            throw new IllegalStateException("Redisson config is not defined");
        }
    }

}
