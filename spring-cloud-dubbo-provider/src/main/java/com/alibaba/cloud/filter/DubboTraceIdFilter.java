package com.alibaba.cloud.filter;

import com.alibaba.fastjson.JSON;
import com.enjoy.cores.result.CommConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.MDC;
import org.springframework.util.StopWatch;

import java.util.UUID;

/**
 * @program: basic-server
 * @description: dubbo日志收集
 * @author: LiZhaofu
 * @create: 2020-04-28 17:10
 **/

//@Activate("provider")
@Slf4j
public class DubboTraceIdFilter implements Filter {

    /**
     * @param invoker
     * @param invocation
     * @return
     * @throws RpcException
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {


        String traceId = RpcContext.getContext().getAttachment(CommConstants.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = this.getUUID();
        }

        //设置日志traceId变量
        MDC.put(CommConstants.TRACE_ID, traceId);

        RpcContext.getContext().setAttachment(CommConstants.TRACE_ID, traceId);

        //用 commons-lang 提供的 StopWatch 计时，Spring 也提供了一个 StopWatch
        StopWatch clock = new StopWatch();
        clock.start(); //计时开始

        Result result = null;

        try {

            result = invoker.invoke(invocation);

            if (result.hasException() && GenericService.class != invoker.getInterface()) {
                log.error("ErrMsg:[{}],serviceName:[{}],methodName:[{}],params:[{}]", result.getException().getMessage(), invoker.getInterface().getName(), invocation.getMethodName(), JSON.toJSONString(invocation.getArguments()));
                log.error("DubboTraceErorr", result.getException());
                result.setAttachment(CommConstants.DUBBO_ERROR,result.getException().getMessage());
                result.setException(null);
            }

        } catch (RuntimeException e) {
            log.error("ErrMsg:[{}],serviceName:[{}],methodName:[{}]", e.getMessage(), invoker.getInterface().getName(), invocation.getMethodName());
            log.error("invokeErorr", e);
            result.setException(null);
        } finally {
            MDC.remove(CommConstants.TRACE_ID);

            clock.stop(); //计时结束
            if (log.isInfoEnabled()) {
                log.info("traceId:{},调用方法:[{}],执行时间:[{}ms]",traceId,invocation.getMethodName(),clock.getTotalTimeMillis());
            }
        }

        return result;
    }

    /**
     * 获取UUID
     *
     * @return String UUID
     */
    public String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //替换-字符
        return uuid.replaceAll("-", "");
    }

}
