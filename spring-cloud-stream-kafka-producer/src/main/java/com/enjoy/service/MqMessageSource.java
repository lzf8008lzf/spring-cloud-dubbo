package com.enjoy.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-26 16:06
 **/

public interface MqMessageSource {//自定义通道
    String MY_OUT_PUT = "myOutPut";
    @Output(MY_OUT_PUT)
    MessageChannel testOutPut();
}
