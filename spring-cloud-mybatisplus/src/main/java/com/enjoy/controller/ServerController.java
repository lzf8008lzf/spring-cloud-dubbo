package com.enjoy.controller;

import com.enjoy.core.result.AjaxResult;
import com.enjoy.core.result.Results;
import com.enjoy.core.result.ResultsUtil;
import com.enjoy.entity.User;
import com.enjoy.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务端
 *
 * @author : SPL
 * @since : 2020-04-29 11:31
 **/
@RequestMapping("/server")
@RestController
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping("/test")
//    @ApiSignature
    public Object test(Integer userId, BigDecimal amount, String productId){

        //业务处理
        Map<String, Object> data = serverService.test(userId, amount, productId);

        return AjaxResult.success(data);
    }

    @PostMapping(value="contentImp")
    public Results contentImp(@RequestBody List<User> list){
        return ResultsUtil.ok(list);
    }

}
