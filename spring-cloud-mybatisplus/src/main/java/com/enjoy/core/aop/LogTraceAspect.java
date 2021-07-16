package com.enjoy.core.aop;

import cn.hutool.json.JSONUtil;
import com.enjoy.core.annotation.LogTrace;
import com.enjoy.core.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志追踪
 */
@Aspect
@Component
@Slf4j
public class LogTraceAspect {

	// 配置织入点
	@Pointcut("@annotation(com.enjoy.core.annotation.LogTrace)")
	public void pointCut() {

	}

	@Before("pointCut()")
	public void before(JoinPoint point) throws NoSuchMethodException, SecurityException{

		Class<?> targetClass=point.getTarget().getClass();
		String className=targetClass.getSimpleName();
		String methodName = point.getSignature().getName();

		Object[] params = point.getArgs();
		String parseParamStr = JacksonUtil.toJson(params);

		log.info("=================================================================================");
		log.info("请求类：[{}]  请求方法：[{}],  请求参数：[{}]",new Object[]{className ,methodName,parseParamStr});
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		//是否打印返回值
		boolean printRet = false;
		//执行具体方法
		Object result = null;
		StopWatch clock = new StopWatch();
		clock.start(); //计时开始
		try {
			MethodSignature signature = (MethodSignature) point.getSignature();
			LogTrace annotation = signature.getMethod().getAnnotation(LogTrace.class);
			if(Objects.nonNull(annotation)) {
				printRet = annotation.printRet();
			}

			result = point.proceed();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}finally {
			clock.stop(); //计时结束
			if (log.isInfoEnabled()) {
				if(printRet){
					log.info("调用方法:[{}],返回值:[{}],执行时间:[{}ms]", point.getSignature().getName(), JSONUtil.toJsonStr(result), clock.getTotalTimeMillis());
				}else {
					log.info("调用方法:[{}],执行时间:[{}ms]", point.getSignature().getName(), clock.getTotalTimeMillis());
				}
			}
		}

		return result;
	}

	/**
	 * 获取目标Class
	 *
	 * @param target
	 * @return
	 */
	private Class<?> getTargetClass(Object target) {
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
		if (targetClass == null) {
			targetClass = target.getClass();
		}
		return targetClass;
	}

	/**
	 * 获取指定方法
	 *
	 * @param pjp
	 * @param targetClass
	 * @return
	 */
	private Method getSpecificMethod(ProceedingJoinPoint pjp, Class<?> targetClass) {
		Signature signature = pjp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();
		Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
		specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
		return specificMethod;
	}
}
