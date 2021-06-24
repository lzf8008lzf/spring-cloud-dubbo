package com.enjoy;

import com.enjoy.core.utils.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY="lock_hello";

    @Test
    public void doTask() {
        boolean lock=false;
        try {
            //获取锁
            lock=stringRedisTemplate.opsForValue().setIfAbsent(KEY, "1", 10, TimeUnit.SECONDS);
            if(!lock) {
                //获取不到锁，直接退出
                return;
            }
            //设置超时，防止程序意外终止而导致key锁无法释放
            stringRedisTemplate.expire(KEY, 5,TimeUnit.MINUTES);
            //to do something
            System.out.println("do task...");
        } finally {
            //最终释放锁
            stringRedisTemplate.delete(KEY);
        }
    }

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
