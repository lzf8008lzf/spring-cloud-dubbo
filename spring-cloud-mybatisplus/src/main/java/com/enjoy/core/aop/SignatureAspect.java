package com.enjoy.core.aop;

import com.enjoy.core.annotation.ApiSignature;
import com.enjoy.core.result.AjaxResult;
import com.enjoy.core.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据过滤处理
 *
 */
@Aspect
@Component
@Slf4j
public class SignatureAspect {


    private static final String APPID = "9527";
    private static final String SECRET = "mysecret123456";

    @Resource
    private HttpServletRequest request;

    // 配置织入点
    @Pointcut("@annotation(com.enjoy.core.annotation.ApiSignature)")
    public void dataScopePointCut() {
    }

    @Around("dataScopePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return handleSignature(point);
    }

    protected Object handleSignature(final ProceedingJoinPoint joinPoint) {
        try {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            ApiSignature apiSignature = method.getAnnotation(ApiSignature.class);
            log.info(apiSignature.method());

            // 供应商的id，验证用户的真实性
            String appid = request.getHeader("appid");
            // 请求发起的时间
            String timestamp = request.getHeader("timestamp");
            // 随机数
            String nonce = request.getHeader("nonce");
            // 签名算法生成的签名
            String sign = request.getHeader("sign");
            if (StringUtils.isEmpty(appid) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(sign)) {
                return AjaxResult.error("请求头缺少授信参数");
            }
            // 限制为（含）60秒以内发送的请求
            long time = 60;
            long now = System.currentTimeMillis() / 1000;
            if (now - Long.parseLong(timestamp) > time) {
                return AjaxResult.error("请求发起时间超过服务器限制时间");
            }
            // 校验appid
            if (!APPID.equalsIgnoreCase(appid)) {
                return AjaxResult.error("appid参数错误");
            }
//            // 校验请求是否重复
//            if (redisUtil.hHasKey("third_party_key", APPID + nonce)) {
//                return AjaxResult.error("请不要发送重复的请求");
//            } else {
//                // 如果nonce没有存在缓存中，则加入，并设置失效时间（秒）
//                redisUtil.hset("third_party_key", APPID + nonce, nonce, time);
//            }
            Map<String,Object> signObj = new HashMap<>(3);
            signObj.put("appid", appid);
            signObj.put("timestamp", timestamp);
            signObj.put("nonce", nonce);
            // 校验签名
            if (!SignUtil.checkSign(signObj, SECRET,sign)) {
                return AjaxResult.error("签名信息错误");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("解析请求参数异常");
        }
        return null;
    }
}
