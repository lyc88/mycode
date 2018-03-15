package com.whyuan.$5Zookeeper.lock.curatorLOCK;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

//curator-recipes封装功能：Leader选举
public class MasterSelector {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";

    private static final String MASTER_PATH="/CURATOR_MASTER_PATH";

    public static void main(String[] args) {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().connectString(ZKSTRING)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();//开始选举
    }

}
