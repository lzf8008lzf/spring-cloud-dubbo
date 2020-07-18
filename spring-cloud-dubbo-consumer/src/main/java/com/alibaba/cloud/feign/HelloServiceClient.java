package com.alibaba.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-07-18 14:50
 **/

@FeignClient("${provider.application.name}")
public interface HelloServiceClient extends HelloService{
}
