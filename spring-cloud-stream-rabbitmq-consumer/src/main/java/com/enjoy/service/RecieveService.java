package com.enjoy.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-25 17:36
 **/

//消息接受端，stream给我们提供了Sink,Sink源码里面是绑定input的，要跟我们配置文件的imput关联的。
@Component
@EnableBinding(Sink.class)
public class RecieveService {
    @StreamListener(Sink.INPUT)
    public void input(Message<Company> message) throws IOException {
        Channel channel = (com.rabbitmq.client.Channel)message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
        System.err.println(JSON.toJSONString(message.getPayload()));
    }
}
