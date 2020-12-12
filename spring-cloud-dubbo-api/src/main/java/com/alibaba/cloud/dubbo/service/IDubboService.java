package com.alibaba.cloud.dubbo.service;

import com.enjoy.cores.result.Results;

/**
 *
 */
public interface IDubboService {

    Results sayHello(String name);

    Results welcomeAd();

    Results bigData();

    Results exception();

    Results blockService(int seconds);
}
