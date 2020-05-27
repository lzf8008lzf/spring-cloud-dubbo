package com.enjoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @program: hello-springboot
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-25 16:52
 **/

//这个注解给我们绑定消息通道的，Source是Stream给我们提供的，可以点进去看源码，可以看到output和input,这和配置文件中的output，input对应的。
@EnableBinding(Source.class)
public class SendService {

    @Autowired
    private Source source;

    public void sendMsg(String msg){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(d));
        source.output().send(MessageBuilder.withPayload(msg).build());
    }

    public void sendDelayMsg(String msg){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(d));
        source.output().send(MessageBuilder.withPayload(msg).setHeader("x-delay",3000).build());
    }
}
