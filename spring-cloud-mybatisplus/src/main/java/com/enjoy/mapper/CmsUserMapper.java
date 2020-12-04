package com.enjoy.mapper;

import com.enjoy.entity.CmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author LiZhaofu
 * @since 2020-06-01
 */
@CacheNamespace(implementation = com.enjoy.core.framework.cache.MybatisRedisCache.class)
public interface CmsUserMapper extends BaseMapper<CmsUser> {

}
