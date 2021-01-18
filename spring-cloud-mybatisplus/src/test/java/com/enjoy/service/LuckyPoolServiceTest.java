package com.enjoy.service;

import com.enjoy.MybatisplusApplication;
import com.enjoy.core.utils.NumberUtil;
import com.enjoy.entity.LuckyPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-18 16:01
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class LuckyPoolServiceTest {

    @Autowired
    private LuckyPoolService luckyPoolService;

    @Test
    public void initData(){

        List luckyList=new ArrayList();
        for(int i=0;i<1000;i++){
            LuckyPool luckyPool=new LuckyPool();
            luckyPool.setNum(i);
            luckyPool.setSum(NumberUtil.sum(i));
            luckyPool.setOddFlag(NumberUtil.isOdd(i));
            luckyPool.setOddEven(NumberUtil.oddEven(NumberUtil.format3D(i)));
            luckyList.add(luckyPool);
        }

        luckyPoolService.saveBatch(luckyList);
    }
}
