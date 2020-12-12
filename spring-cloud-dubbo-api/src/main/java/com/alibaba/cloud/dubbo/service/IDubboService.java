package com.alibaba.cloud.dubbo.service;

import com.alibaba.cloud.dubbo.WelcomeAd;
import com.enjoy.cores.result.Results;

/**
 *
 */
public interface IDubboService {

    String sayHello(String name);

    WelcomeAd welcomeAd();

    String bigData();

    Results exception();

    Results blockService(int seconds);
}
