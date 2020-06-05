package com.enjoy.controller;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-06-05 11:07
 **/


import com.enjoy.entity.TestBean;
import com.enjoy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testes")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("findAll")
    public Iterable<TestBean> findAll() {

        return testService.findAll();
    }

    @RequestMapping("list")
    public String save() {
        List<TestBean> list = null;
        testService.save(list);

        return "success";
    }

    @RequestMapping("save")
    public void save(TestBean bean) {
        testService.save(bean);
    }

    @RequestMapping("findByName")
    public List<TestBean> findByName(String name) {
        return testService.findByName(name);
    }

    @RequestMapping("findByNameOrDesc")
    public List<TestBean> findByNameOrDesc(String nameordesc) {
        return testService.findByNameOrDesc(nameordesc);
    }

}


