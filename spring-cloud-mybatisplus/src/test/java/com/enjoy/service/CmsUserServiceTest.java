package com.enjoy.service;

import com.alibaba.fastjson.JSON;
import com.enjoy.MybatisplusApplication;
import com.enjoy.entity.CmsUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-06-01 15:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CmsUserServiceTest {

    @Autowired
    private CmsUserService cmsUserService;

    @Test
    public void getById()
    {
        CmsUser cmsUser = cmsUserService.getById(1);
        log.info(JSON.toJSONString(cmsUser));
    }
}
