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
public @interface Signature {
    /**
     * 签名算法
     */
    String method() default "MD5";

}
