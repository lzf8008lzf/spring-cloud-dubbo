package com.feign.client;

import com.feign.pojo.WelcomeAd;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 客户端调用
 */
@FeignClient("${provider.application.name}")
public interface FeignClientService {

    @RequestMapping(value="/getMessage")
    String getMessage();

    @RequestMapping(value = "welcomeAd", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
    public WelcomeAd welcomeAd();
}
