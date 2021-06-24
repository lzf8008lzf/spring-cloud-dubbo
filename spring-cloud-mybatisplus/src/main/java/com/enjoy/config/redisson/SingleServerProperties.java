package com.enjoy.config.redisson;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: APIGateway
 * @description:
 * @author: LiZhaofu
 * @create: 2021-06-22 18:26
 **/

@Data
@Component
public class SingleServerProperties implements Serializable {
    private static final long serialVersionUID = -385286650728472483L;
    @Autowired
    private SingleServerConfig singleServerConfig;

    @Value("${redisson.threads:0}")
    private int threads;

    @Value("${redisson.nettyThreads:0}")
    private int nettyThreads;

    @Value("${redisson.transportMode:NIO}")
    private String transportMode;

    @Autowired
    private Codec codec;
}
