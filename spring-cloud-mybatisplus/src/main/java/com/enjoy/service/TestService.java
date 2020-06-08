package com.enjoy.service;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-06-05 11:03
 **/
import com.enjoy.entity.TestBean;
import java.util.List;


public interface TestService {
    Iterable<TestBean> findAll();

    void save(List<TestBean> list);

    void save(TestBean bean);

    List<TestBean> findByName(String text);

    List<TestBean> findByNameOrDesc(String nameordesc);
}

