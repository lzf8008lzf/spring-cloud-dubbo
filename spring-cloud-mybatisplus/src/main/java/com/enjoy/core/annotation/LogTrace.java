package com.enjoy.core.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTrace {

	
	/**
	 * * 是否打印返回值
	 * @return
	 */
	boolean printRet() default false;
}
