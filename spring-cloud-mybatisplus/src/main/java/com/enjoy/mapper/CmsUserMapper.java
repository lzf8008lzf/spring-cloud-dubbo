package com.enjoy.mapper;

import com.enjoy.entity.CmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author LiZhaofu
 * @since 2020-06-01
 */
@CacheNamespace(implementation = com.enjoy.core.framework.cache.RedissonCache.class ,
        properties ={@Property(name = "timeToLive",value = "200000"),
                @Property(name = "maxIdleTime",value = "100000"),
                @Property(name = "maxSize",value = "100000"),
                @Property(name = "redissonConfig",value = "/config/redisson.yaml")})
public interface CmsUserMapper extends BaseMapper<CmsUser> {

}
