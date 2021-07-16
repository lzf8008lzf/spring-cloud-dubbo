package com.enjoy.service;

import com.enjoy.core.annotation.DistributedLock;
import com.enjoy.core.annotation.LogTrace;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-13 16:31
 **/

@Service
public class ServerService {

    @DistributedLock
    @LogTrace
    public Map test(Integer userId, BigDecimal amount, String productId){
        Map<String, Object> data = new HashMap<>(3);

        data.put("userId", userId);
        data.put("amount", amount);
        data.put("productId", productId);

        return data;
    }
}
