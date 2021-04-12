package com.enjoy;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.enjoy.core.utils.UpdateLogUtil;
import com.enjoy.entity.CmsUser;
import com.enjoy.service.CmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-04-12 14:21
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UpdateLogUtilTest {

    @Autowired
    private CmsUserService cmsUserService;

    @Test
    public void UpdateLog(){
        CmsUser cmsUser = cmsUserService.getById(1);
        log.info(JSON.toJSONString(cmsUser));
        CmsUser tmpUser = new CmsUser();
        BeanUtils.copyProperties(cmsUser,tmpUser);
        tmpUser.setNickName("朱厚照");
        tmpUser.setAvatar("胡文庸");
        tmpUser.setCreateDate(null);
        log.info(JSON.toJSONString(tmpUser));
        String updateStr = UpdateLogUtil.UpdateLog(cmsUser,tmpUser);
        log.info(updateStr);
    }
}
