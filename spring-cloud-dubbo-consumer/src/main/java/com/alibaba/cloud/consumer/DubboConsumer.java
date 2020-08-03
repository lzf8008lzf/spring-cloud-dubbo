package com.alibaba.cloud.consumer;


import com.alibaba.cloud.dubbo.WelcomeAd;
import com.alibaba.cloud.dubbo.service.IDubboService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
@Slf4j
public class DubboConsumer {

    @Reference(filter={"dubboTraceIdFilter"},connections = 5)
    private IDubboService consumerService;

    public String sayHello() {
        Map<String,String> map = System.getenv();
        System.out.println(map.get("USERNAME"));//获取用户名
        System.out.println(map.get("COMPUTERNAME"));//获取计算机名
        System.out.println(map.get("USERDOMAIN"));//获取计算机域名
        return consumerService.sayHello(map.get("USERNAME"));
    }

    public WelcomeAd welcomeAd()
    {
        return consumerService.welcomeAd();
    }

    public String bigData(){
        return consumerService.bigData();
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
