package com.alibaba.cloud.consumer;


import com.alibaba.cloud.dubbo.WelcomeAd;
import com.alibaba.cloud.dubbo.service.IDubboService;
import com.enjoy.cores.constants.DubboConstants;
import com.enjoy.cores.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
@Slf4j
public class DubboConsumer {

    @DubboReference(check = DubboConstants.DUBBO_CHECK, timeout = DubboConstants.CONSUMER_TIMEOUT, filter = {DubboConstants.DUBBO_FILTER},
            loadbalance = DubboConstants.CONSUMER_LOAD_BALANCE)
    private IDubboService consumerService;

    @Bean("consumerService")
    public IDubboService consumerService() {
        return consumerService;
    }

}
