package com.feign.server;

import com.feign.pojo.WelcomeAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class FeignServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(FeignServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FeignServerApplication.class, args);
        logger.info("nacos-discovery-server启动");
    }

    @GetMapping("getMessage")
    public String getMessage(){
        return "来调戏我啊！！！";
    }

    @RequestMapping(value = "welcomeAd", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
    public WelcomeAd welcomeAd() {
        WelcomeAd welcomeAd = new WelcomeAd();
        welcomeAd.setContent("https://www.oppein.cn/");
        welcomeAd.setDuration(1000);
        welcomeAd.setImg("https://yuexiang-video.oss-cn-beijing.aliyuncs.com/2020/06/29/16-57-2507251153116897");
        welcomeAd.setTitle("欧派全屋定制");
        welcomeAd.setType("img");

        return welcomeAd;
    }
}
