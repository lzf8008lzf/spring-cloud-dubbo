package com.enjoy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

/**
 * @program: hello-springboot
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-25 16:52
 **/

//这个注解给我们绑定消息通道的，Source是Stream给我们提供的，可以点进去看源码，可以看到output和input,这和配置文件中的output，input对应的。
@EnableBinding(MqMessageSource.class)
public class SendService {

    @Autowired
    @Output(MqMessageSource.MY_OUT_PUT)
    private MessageChannel channel;

    public void sendMsg(String msg){
        channel.send(MessageBuilder.withPayload(msg).setHeader("routeId", UUID.randomUUID().toString()).build());
    }
}
