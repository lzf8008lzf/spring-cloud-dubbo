package com.enjoy.core.annotation;

import java.lang.annotation.*;

/**
 * 接口限流
 *
 */
@Inherited
@Documented
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLimit {
    //标识 指定second时间段内的访问次数限制
    int limit() default 5;
    //标识 时间段
    long second() default 5;
}
