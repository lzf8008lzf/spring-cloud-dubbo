package com.enjoy;

/**
 * @program: spring-cloud-dubbo
 * @description:
 * @author: LiZhaofu
 * @create: 2020-05-28 14:12
 **/

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CuratorBasicAPI {

    private CuratorFramework client;

    private String connectString = "dubbohost:2181";

    private int sessionTimeoutMs = 60000;

    private int connectionTimeoutMs = 60000;

    private String path = "/test";

    private String rootPath = "/";

    private byte[] data = "data".getBytes();

    private byte[] newData = "newData".getBytes();

    private int baseSleepTimeMs = 1000;

    private int maxRetries = 3;

    @Before
    public void connect() {
        //baseSleepTimeMs：基础睡眠时间；maxRetries：最大重试次数；maxSleepMs：两次重试之间最大睡眠时间
        //其睡眠时间计算公式为：sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)));
        RetryPolicy retryPolicy1 = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

        //ExponentialBackoffRetry的子类，重写了父类的getSleepTimeMs方法
        //其睡眠时间计算公式为：Math.min(maxSleepTimeMs, super.getSleepTimeMs(retryCount, elapsedTimeMs));
        RetryPolicy retryPolicy2 = new BoundedExponentialBackoffRetry(baseSleepTimeMs, 50000, maxRetries);

        //最大重试3次，每次相隔时间为5000毫秒
        RetryPolicy retryPolicy3 = new RetryNTimes(3, 5000);

        //只重试一次，RetryNTimes的子类，就是将次数指定为1
        RetryPolicy retryPolicy4 = new RetryOneTime(3000);

        //一直重试，重试间隔为3000毫秒
        RetryPolicy retryPolicy5 = new RetryForever(3000);

        //最大重试时间为20000毫秒，若超过就不重试了，每次间隔3000毫秒
        RetryPolicy retryPolicy6 = new RetryUntilElapsed(20000, 3000);

        //client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);

        //流式
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString) //服务器列表，格式host1:port1,host2:port2,…
                .sessionTimeoutMs(sessionTimeoutMs) //会话超时时间，单位毫秒，默认60000ms
                .connectionTimeoutMs(connectionTimeoutMs) //连接创建超时时间，单位毫秒，默认60000ms
                .retryPolicy(retryPolicy1)
                .build();

        //启动
        client.start();
    }

    @Test
    public void showBasicAPI() throws Exception {
        exist();

        create();

        exist();

        getData();

        setData();

        getData();

        getChildren();

        delete();

        getChildren();

    }

    @After
    public void close() {
        //关闭连接
        client.close();
    }

    private void create() throws Exception {
        //创建一个节点
        //client.create().forPath(path);

        //创建一个节点附带数据
        client.create().forPath(path, data);

        System.out.println("创建节点：" + path);

        //创建一个临时节点，默认不指定是持久的，另外还有持久带序列和临时带序列PERSISTENT_SEQUENTIAL、EPHEMERAL_SEQUENTIAL
        //client.create().withMode(CreateMode.EPHEMERAL).forPath(path);

        //如果没有父目录则一起创建
        //client.create().creatingParentsIfNeeded().forPath(path);

        //后天模式，还可以传入自定义CallBack和线程池
        //client.create().inBackground().forPath(path);

        //带权限的
        //List<ACL> aclList = new ArrayList<ACL>();
        //acls.add(new ACL(Perms.ALL, new Id("digest", "520:1314")));
        //client.create().withACL(aclList).forPath(path);

        //当然你也这可这样
        //client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(aclList).inBackground().forPath(path, data);
    }

    private void exist() throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            System.out.println("节点" + path + "不存在。");
        } else {
            System.out.println("节点" + path + "存在。");
        }
    }

    private void getData() throws Exception {
        byte[] byteData = client.getData().forPath(path);

        //client.getData().inBackground().forPath(path);

        //Stat stat = new Stat();
        //client.getData().storingStatIn(stat).forPath("path");

        System.out.println("节点" + path + "的数据为：" + new String(byteData));
    }

    private void setData() throws Exception {
        client.setData().forPath(path, newData);

        System.out.println("设置节点" + path + "数据为：" + new String(newData));

        //client.setData().inBackground().forPath(path, newData);

        //指定版本，若版本错误将抛出异常bad version
        //client.setData().withVersion(520).forPath(path,newData);
    }

    private void delete() throws Exception {
        client.delete().forPath(path);

        System.out.println("删除节点：" + path);

        //client.delete().withVersion(1314).forPath(path);

        //只要会话有效将一直尝试删除，直到删除成功
        //client.delete().guaranteed().forPath(path);

        //同
        //client.delete().deletingChildrenIfNeeded().forPath(path);

        //client.delete().inBackground().forPath(path);

        //client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(1314).inBackground().forPath(path);
    }

    private void getChildren() throws Exception {
        List<String> names = client.getChildren().forPath(rootPath);

        System.out.println("根目录下有节点：" + names);

        //client.getChildren().inBackground().forPath(rootPath);
    }
}
