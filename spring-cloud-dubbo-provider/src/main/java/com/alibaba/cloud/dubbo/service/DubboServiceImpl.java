package com.alibaba.cloud.dubbo.service;

import com.alibaba.cloud.dubbo.WelcomeAd;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu (filter={"dubboTraceIdFilter"})
 * @create: 2020-08-01 11:39
 **/

@Service(filter={"dubboTraceIdFilter"})
@Slf4j
public class DubboServiceImpl implements IDubboService{
    @Override
    public String sayHello(String name) {
        log.info(" Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());

        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public WelcomeAd welcomeAd() {
        WelcomeAd welcomeAd =new WelcomeAd();

        welcomeAd.setType("img");
        welcomeAd.setTitle("欧派全屋定制");
        welcomeAd.setImg("https://yuexiang-video.oss-cn-beijing.aliyuncs.com/2020/06/29/16-57-2507251153116897");
        welcomeAd.setContent("https://www.oppein.cn/");
        welcomeAd.setShowUrl("url");
        welcomeAd.setDuration(3000);

        return welcomeAd;
    }
}
