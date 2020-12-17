package com.enjoy.core.handler;

import com.enjoy.core.annotation.VisitLimit;
import com.enjoy.core.exception.BusinessException;
import com.enjoy.core.utils.IPUtils;
import com.enjoy.core.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-12-17 13:57
 **/

@Component
public class VisitLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    /**
     * 处理请求之前被调用
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(VisitLimit.class)) {
                return true;
            }
            VisitLimit accessLimit = method.getAnnotation(VisitLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int limit = accessLimit.limit();
            long sec = accessLimit.second();
            String key = IPUtils.getIpAddr(request) + request.getRequestURI();
            Integer maxLimit = null;
            Object value = redisService.get(key);
            if (value != null && !value.equals("")) {
                maxLimit = Integer.valueOf(String.valueOf(value));
            }
            if (maxLimit == null) {
                redisService.set(key, "1", sec);
            } else if (maxLimit < limit) {
                Integer i = maxLimit + 1;
                redisService.set(key, i.toString(), sec);
            } else {
//              output(response, "请求太频繁!");
//              return false;
                throw new BusinessException(500, "请求太频繁!");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}