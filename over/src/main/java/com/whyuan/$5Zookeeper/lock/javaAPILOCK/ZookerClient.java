package com.whyuan.$5Zookeeper.lock.javaAPILOCK;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

//获取zookeeper实例
public class ZookerClient {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";


    private static int sessionTimeOut= 5000;

    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(1);
        ZooKeeper zooKeeper=new ZooKeeper(ZKSTRING, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }

            }
        });
        countDownLatch.await();
        return zooKeeper;
    }

    public static int getSessionTimeOut() {
        return sessionTimeOut;
    }
}
