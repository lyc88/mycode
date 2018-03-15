package com.whyuan.$5Zookeeper.api.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CuratorAPI操作
 * 一、2种会话创建方式。
 * 二、3种事件机制
 * 三、节点的增删改查
 * 四、异步机制，事件机制
 */
public class CuratorFrameworkAPI {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";

    private static CuratorFramework curatorFramework;

    //1.curator创建会话的两种方式 normal
    public static CuratorFramework getInstanceInNormal(){
        curatorFramework= CuratorFrameworkFactory
                .newClient(ZKSTRING,5000,5000,
                        new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }
    //2.fluent风格
    public static CuratorFramework getInstanceInFluent(){
        curatorFramework=CuratorFrameworkFactory.builder()
                .connectString(ZKSTRING)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).
                namespace("/curator").build();
        curatorFramework.start();
        return curatorFramework;
    }

    public static void main(String[] args) throws Exception {
        /**
         * 三种watcher来做节点的监听
         * pathcache   监视一个路径下子节点的创建、删除、节点数据更新
         * NodeCache   监视一个节点的创建、更新、删除
         * TreeCache   pathcaceh+nodecache 的合体（监视路径下的创建、更新、删除事件），
         * 缓存路径下的所有子节点的数据
         */
        getInstanceInNormal();

        //3.节点变化事件NodeCache
        NodeCache nodeCache=new NodeCache(curatorFramework,"/curator",false);
        nodeCache.start();

        //4.Watcher
        nodeCache.getListenable().addListener(()->
                System.out.println("节点数据发生变化,变化后的结果" +"："+new String(nodeCache.getCurrentData().getData()))
        );

        //5.修改节点数据
        curatorFramework.setData().forPath("/curator","123".getBytes());

        //6.PatchChildrenCache:
        // 三种启动模式NORMAL,初始化时候为空
        // BUILD_INITIAL_CACHE,给传入参数初始化
        // POST_INITIALIZED_EVENT;发送一个已经初始化的事件
        PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorFramework,"/event",true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);

        pathChildrenCache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent)->{
            switch (pathChildrenCacheEvent.getType()){
                case CHILD_ADDED:
                    System.out.println("增加子节点");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                default:break;
            }
        });

        //7.创建持久节点
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event","event".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("1");

        //8.创建临时节点
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/event/event1","1".getBytes());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("2");

        //9.删除节点
        curatorFramework.delete().forPath("/event/event1");
        //默认情况下，version为-1
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/event/event1");

        //10.查询
        Stat stat=new Stat();
        byte[] bytes=curatorFramework.getData().storingStatIn(stat).forPath("/curator");
        System.out.println(new String(bytes)+"-->stat:"+stat);

        System.out.println("3");


        //11.异步操作：适用于对zookeeper操作时长比较久的行为
        //对节点的操作交给线程池中的线程去处理，而不是当前线程处理
        ExecutorService service= Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch=new CountDownLatch(1);
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                    inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                            System.out.println(Thread.currentThread().getName()+"->resultCode:"+curatorEvent.getResultCode()+"->"
                            +curatorEvent.getType());
                            countDownLatch.countDown();
                        }
                    },service).forPath("/mic","123".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDownLatch.await();
        service.shutdown();

        //12.事务操作（curator独有）
        //开启一个事务，将创建节点，修改借点数据放在一起操作
        try {
            Collection<CuratorTransactionResult> resultCollections=curatorFramework.inTransaction().create().forPath("/trans","111".getBytes()).and().
                    setData().forPath("/curator","111".getBytes()).and().commit();
            for (CuratorTransactionResult result:resultCollections){
                System.out.println(result.getForPath()+"->"+result.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从键盘读出一个字符，然后返回它的Unicode码。按下Enter结束输入
        System.in.read();


    }

}
