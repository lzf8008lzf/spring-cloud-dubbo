package com.feign.client;

import com.feign.pojo.WelcomeAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 */
@SpringBootApplication
@RestController
@EnableFeignClients
public class FeignClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(FeignClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
        logger.info("nacos-discovery-client启动");
    }

    @Autowired
    private  FeignClientService feignClientService;

    @GetMapping("getMessage")
    public String getMessage(){
        return feignClientService.getMessage();
    }

    @RequestMapping(value = "welcomeAd", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
    public WelcomeAd welcomeAd() {
        return feignClientService.welcomeAd();
    }
}
