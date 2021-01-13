package com.enjoy.core.annotation;

import java.lang.annotation.*;

/**
 * 接口签名验签注解
 *
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * lock 名称
     */
    String name() default "lock";

    /**
     * 等待时长
     */
    long waitTime() default 10;

    /**
     * 自动解锁时长
     */
    long leaseTime() default 6;

}
