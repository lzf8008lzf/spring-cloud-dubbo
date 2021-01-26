package com.enjoy.service;

import com.enjoy.MybatisplusApplication;
import com.enjoy.core.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-18 14:50
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class Lucky3dServiceTest {

    public static void main(String[] args) {

        System.out.println(NumberUtil.isPrime(13));

        System.out.println(NumberUtil.isOdd(35));

        System.out.println(NumberUtil.format3D(999));

        System.out.println(NumberUtil.sum(789));

        System.out.println(NumberUtil.oddEven("789"));
    }

}
