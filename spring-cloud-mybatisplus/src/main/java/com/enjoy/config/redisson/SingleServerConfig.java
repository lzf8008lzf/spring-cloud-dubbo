package com.enjoy.config.redisson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: APIGateway
 * @description:
 * @author: LiZhaofu
 * @create: 2021-06-22 18:25
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class SingleServerConfig extends BaseConfig {
    private static final long serialVersionUID = -3173391729030180033L;

    /*** Redis server address */
    @Value("${redisson.singleServerConfig.address:redis://localhost:6379}")
    private String address;

    /**Minimum idle subscription connection amount */
    @Value("${redisson.singleServerConfig.subscriptionConnectionMinimumIdleSize:1}")
    private int subscriptionConnectionMinimumIdleSize = 1;

    /*** Redis subscription connection maximum pool size * */
    @Value("${redisson.singleServerConfig.subscriptionConnectionPoolSize:50}")
    private int subscriptionConnectionPoolSize = 50;

    /**Minimum idle Redis connection amount */
    @Value("${redisson.singleServerConfig.connectionMinimumIdleSize:24}")
    private int connectionMinimumIdleSize = 24;

    /* Redis connection maximum pool size */
    @Value("${redisson.singleServerConfig.connectionPoolSize:64}")
    private int connectionPoolSize = 64;

    /**Database index used for Redis connection */
    @Value("${redisson.singleServerConfig.database:7}")
    private int database = 0;

    /**Interval in milliseconds to check DNS */
    @Value("${redisson.singleServerConfig.dnsMonitoringInterval:5000}")
    private long dnsMonitoringInterval = 5000;
}
