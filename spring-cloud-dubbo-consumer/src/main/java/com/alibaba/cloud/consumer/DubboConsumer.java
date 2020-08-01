package com.alibaba.cloud.consumer;


import com.alibaba.cloud.dubbo.WelcomeAd;
import com.alibaba.cloud.dubbo.service.IDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component
@Slf4j
public class DubboConsumer {

    @Reference(filter={"dubboTraceIdFilter"})
    private IDubboService consumerService;

    public void sayHello() {
        String retStr = consumerService.sayHello("JeffLee");
        log.info(retStr);
    }

    public WelcomeAd welcomeAd()
    {
        return consumerService.welcomeAd();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext(new String[] {"dubbo-consumer.xml"});

        context.start();

        log.error("------------------------------------------------------------------------------------------------");
        IDubboService dubboService=(IDubboService) context.getBean("consumerService");

        String test=dubboService.sayHello("ttttttttttt");

        log.error(test);

    }
}
