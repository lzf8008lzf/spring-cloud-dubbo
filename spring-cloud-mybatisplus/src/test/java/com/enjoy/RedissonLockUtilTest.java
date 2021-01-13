package com.enjoy;

import com.enjoy.core.utils.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2021-01-13 14:42
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisplusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedissonLockUtilTest {

    @Test
    public void lock(){
        RLock lock = RedissonLockUtil.getRLock("rlock");

        // 尝试加锁，最多等待9秒，上锁以后6秒自动解锁
        boolean res = RedissonLockUtil.tryLock(lock,9, 6);

        if(res) {
            try{
                log.info("-----------");
            }finally {
                lock.unlock();
            }
        }else {
            log.error("获取锁失败");
        }

    }
}
