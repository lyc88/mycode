package com.whyuan.$5Zookeeper.lock.javaAPILOCK;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

//用于监听分布式锁中，节点删除事件
public class LockWatcher implements Watcher {
    private CountDownLatch latch;


    public LockWatcher(CountDownLatch latch) {
        this.latch=latch;
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getType()== Event.EventType.NodeDeleted){
            latch.countDown();
        }

    }

}
