package com.enjoy.distributedlock;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-28 14:58
 **/

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedissonManager {

    private static Config config = new Config();

    private static RedissonClient redisson = null;

    // 在执行构造函数后执行
    @PostConstruct
    public static void init() {
        try {
//            config.useClusterServers()
//                    .setScanInterval(200000)
//                    // 设置集群状态扫描间隔
//                    .setMasterConnectionPoolSize(10000)
//                    // 设置对于master节点的连接池中连接数最大为10000
//                    .setSlaveConnectionPoolSize(10000)
//                    // 设置对于slave节点的连接池中连接数最大为500
//                    .setIdleConnectionTimeout(10000)
//                    // 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒。
//                    .setConnectTimeout(30000)
//                    // 同任何节点建立连接时的等待超时。时间单位是毫秒。
//                    .setTimeout(3000)
//                    // 等待节点回复命令的时间。该时间从命令发送成功时开始计时。
//                    .setRetryInterval(3000)
//                    // 当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
//                    .addNodeAddress("redis://dubbohost:6379");
            config.useSingleServer().setAddress("redis://dubbohost:6379");
            redisson = Redisson.create(config);
            log.info("redisson init success!");
        } catch (Exception e) {
            log.error("redisson init error!", e);
        }
    }

    public RedissonClient getRedisson() {
        return redisson;
    }

}
