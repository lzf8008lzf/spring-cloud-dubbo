package com.enjoy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-28 15:03
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributedLockApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RedissonLock {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void test() throws Exception {

        RLock lock = redissonClient.getFairLock("rlock");

//        try {
//            lock.lock();
//        } finally {
//            lock.unlock();
//        }
//
//
//        //另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。
//
//        // 加锁以后10秒钟自动解锁
//        // 无需调用unlock方法手动解锁
//        lock.lock(10, TimeUnit.SECONDS);

        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        boolean res = lock.tryLock(10, 10, TimeUnit.SECONDS);
        if (res) {
            try {
                log.info("获取锁成功！");
            } finally {
                lock.unlock();
            }
        }else {
            log.info("获取锁失败！");
        }
//
//        //分布式锁提供了异步执行
//        lock.lockAsync();
//        lock.lockAsync(10, TimeUnit.SECONDS);
//        Future<Boolean> future = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);
//
//        RLock fairLock = redissonClient.getFairLock("anyLock");
    }


    @Test
    public void MultiLock() throws Exception{
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");

        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功。
        lock.lock();

        lock.unlock();

        //另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。

        // 给lock1，lock2，lock3加锁，如果没有手动解开的话，10秒钟后将会自动解开
        lock.lock(10, TimeUnit.SECONDS);

        // 为加锁等待100秒时间，并在加锁成功10秒钟后自动解开
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);

        lock.unlock();

    }

    @Test
    public void RedLock() throws Exception{
        RLock lock1 = redissonClient.getLock("lock1");
        RLock lock2 = redissonClient.getLock("lock2");
        RLock lock3 = redissonClient.getLock("lock3");

        RedissonRedLock lock = new RedissonRedLock (lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功。
        lock.lock();

        lock.unlock();

        //另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。

        // 给lock1，lock2，lock3加锁，如果没有手动解开的话，10秒钟后将会自动解开
        lock.lock(10, TimeUnit.SECONDS);

        // 为加锁等待100秒时间，并在加锁成功10秒钟后自动解开
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);

        lock.unlock();

    }

    @Test
    public void ReadWriteLock() throws Exception{

        RReadWriteLock rwlock = redissonClient.getReadWriteLock("anyRWLock");

        // 最常见的使用方法
        rwlock.readLock().lock();
        // 或
//        rwlock.writeLock().lock();

        //另外Redisson还通过加锁的方法提供了leaseTime的参数来指定加锁的时间。超过这个时间后锁便自动解开了。

        // 10秒钟以后自动解锁
        // 无需调用unlock方法手动解锁
        rwlock.readLock().lock(10, TimeUnit.SECONDS);
        // 或
        rwlock.writeLock().lock(10, TimeUnit.SECONDS);

        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        boolean res = rwlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
        // 或
//        boolean res = rwlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);


    }

    @Test
    public void Semaphore() throws Exception
    {
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.acquire();
        //或
        semaphore.acquireAsync();
        semaphore.acquire(23);
        semaphore.tryAcquire();
        //或
        semaphore.tryAcquireAsync();
        semaphore.tryAcquire(23, TimeUnit.SECONDS);
        //或
        semaphore.tryAcquireAsync(23, TimeUnit.SECONDS);
        semaphore.release(10);
        semaphore.release();
        //或
        semaphore.releaseAsync();
    }

}
