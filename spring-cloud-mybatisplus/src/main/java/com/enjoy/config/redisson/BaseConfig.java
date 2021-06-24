package com.enjoy.config.redisson;

import lombok.Data;
import org.redisson.config.SslProvider;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;

/**
 * @program: APIGateway
 * @description:
 * @author: LiZhaofu
 * @create: 2021-06-22 18:24
 **/

@Data
public class BaseConfig<T extends org.redisson.config.BaseConfig<T>>  {
    /**
     * If pooled connection not used for a <code>timeout</code> time * and current connections amount bigger than minimum idle connections pool size, * then it will closed and removed from pool. * Value in milliseconds. */
    @Value("${redisson.singleServerConfig.idleConnectionTimeout:10000}")
    private int idleConnectionTimeout = 10000;
    /**
     * Timeout during connecting to any Redis server. * Value in milliseconds. */
    @Value("${redisson.singleServerConfig.connectTimeout:10000}")
    private int connectTimeout = 10000;
    /**
     * Redis server response timeout. Starts to countdown when Redis command was succesfully sent. * Value in milliseconds. */
    @Value("${redisson.singleServerConfig.timeout:3000}")
    private int timeout = 3000;
    @Value("${redisson.singleServerConfig.retryAttempts:3}")
    private int retryAttempts = 3;
    @Value("${redisson.singleServerConfig.retryInterval:1500}")
    private int retryInterval = 1500;
    /**
     * Password for Redis authentication. Should be null if not needed */
    @Value("${redisson.singleServerConfig.password:dubboredis}")
    private String password;
    private String username;
    /**
     * Subscriptions per Redis connection limit */
    @Value("${redisson.singleServerConfig.subscriptionsPerConnection:5}")
    private int subscriptionsPerConnection = 5;
    /**
     * Name of client connection */
    @Value("${redisson.singleServerConfig.clientName:redisson}")
    private String clientName;
    private boolean sslEnableEndpointIdentification = true;
    private SslProvider sslProvider = SslProvider.JDK;
    private URL sslTruststore;
    private String sslTruststorePassword;
    private URL sslKeystore;
    private String sslKeystorePassword;
    private int pingConnectionInterval;
    private boolean keepAlive;
    private boolean tcpNoDelay;
}
