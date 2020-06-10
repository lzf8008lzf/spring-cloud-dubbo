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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @RequestMapping("search")
    public Page<TestBean> search(String name) {
        int pageNum=1;
        // 排序方式，这里是以“recordNo”为标准进行降序
//        Sort sort = Sort.by(Sort.Direction.DESC, "recordNo");  // 这里的"recordNo"是实体类的主键，记住一定要是实体类的属性，而不能是数据库的字段
        Pageable pageable = PageRequest.of(pageNum - 1, 6); // （当前页， 每页记录数， 排序方式）;
        return testService.search(name,pageable);
    }
}


