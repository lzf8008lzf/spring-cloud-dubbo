package com.enjoy.core.utils;

import com.enjoy.core.framework.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @program: gateway-parent
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-16 10:43
 **/

@Slf4j
public class RedissonLockUtil {
    private static RedissonClient redissonClient = SpringContextHolder.getBean("redissonClient");

    public static RLock getRLock(String name){
        return redissonClient.getLock(name);
    }

    public static boolean tryLock(RLock rLock,long waitTime, long leaseTime){
        boolean ret = false;
        try {
            ret = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("tryLock exception!",e);
        }
        return ret;
    }

    public static void unlock(RLock rLock){
        rLock.unlock();
    }
}
