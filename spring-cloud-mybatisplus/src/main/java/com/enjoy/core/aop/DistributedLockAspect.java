package com.enjoy.core.aop;

import com.enjoy.core.annotation.DistributedLock;
import com.enjoy.core.result.AjaxResult;
import com.enjoy.core.utils.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-13 15:00
 **/

@Aspect
@Component
@Slf4j
public class DistributedLockAspect {

    // 配置织入点
    @Pointcut("@annotation(com.enjoy.core.annotation.DistributedLock)")
    public void lockPointCut() {
    }

    @Around("lockPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return handleDistributedLock(point);
    }

    protected Object handleDistributedLock(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);
            log.info(distributedLock.name());

            RLock lock = RedissonLockUtil.getRLock(distributedLock.name());
            // 尝试加锁，最多等待9秒，上锁以后6秒自动解锁
            boolean res = RedissonLockUtil.tryLock(lock,distributedLock.waitTime(), distributedLock.leaseTime());

            if(res) {
                try{
                    result = joinPoint.proceed();
                }finally {
                    lock.unlock();
                }
            }else {
                log.error("获取锁失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("解析请求参数异常");
        }

        return result;
    }
}
