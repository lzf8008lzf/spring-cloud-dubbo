package com.alibaba.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @program: yuexiang
 * @description:
 * @author: LiZhaofu
 * @create: 2020-09-04 10:42
 **/

@Component
@Slf4j
public class InitApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("onApplicationEvent---------------------------------------------------------------------------------");
    }
}
