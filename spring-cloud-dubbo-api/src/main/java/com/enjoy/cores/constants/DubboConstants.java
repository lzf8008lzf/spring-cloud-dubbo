package com.enjoy.cores.constants;

/**
 * @program: gateway-parent
 * @description: dubbo相关常量
 * @author: LiZhaofu
 * @create: 2020-05-09 12:32
 **/

public class DubboConstants {
    //filter名称
    public static final String DUBBO_FILTER="dubboTraceIdFilter";
    //服务端负载均衡策略 AbstractLoadBalance
    // 1.RandomLoadBalance：随机，按权重设置随机概率 random；
    // 2.ConsistentHashLoadBalance：一致性哈希算法 consistenthash
    // 3.LeastActiveLoadBalance：最小活跃数负载均衡 leastactive；
    // 4.RoundRobinLoadBalance：根据权重进轮训，轮训的缺点就是无法顾及invoker的执行效率，有可能将请求积压在某一处理较慢的provider上 roundrobin。
    public static final String PROVIDER_LOAD_BALANCE="leastactive";
    //服务端超时时间
    public static final int PROVIDER_TIMEOUT=30000;
    //服务端重试次数
    public static final int PROVIDER_RETRIES=0;
    //消费端是否异步执行
    public static final boolean PROVIDER_ASYNC=false;
    //服务端的连接数
    public static final int PROVIDER_CONNECTIONS=2;
    //消费端负载均衡策略
    public static final String CONSUMER_LOAD_BALANCE="leastactive";
    //消费端超时时间
    public static final int CONSUMER_TIMEOUT=10000;
    //消费端重试次数
    public static final int CONSUMER_RETRIES=0;
    //消费端是否异步执行
    public static final boolean CONSUMER_ASYNC=false;
    //启动时是否检查
    public static final boolean DUBBO_CHECK=false;
    //服务端延迟注册时间
    public static final int PROVIDER_DELAY=3000;

    //消费端的连接数
    public static final int CONSUMER_CONNECTIONS=2;
}
