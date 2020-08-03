package com.alibaba.cloud.dubbo.service;

import com.alibaba.cloud.dubbo.WelcomeAd;

/**
 *
 */
public interface IDubboService {

    String sayHello(String name);

    WelcomeAd welcomeAd();

    String bigData();
}
