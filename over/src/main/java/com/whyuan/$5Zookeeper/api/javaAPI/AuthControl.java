package com.whyuan.$5Zookeeper.api.javaAPI;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
/*
* Zookeeper权限控制
*
*  acl (create /delete /admin /read/write)
权限模式： ip/Digest（username:password）/world/super
* schema:授权对象
*       digest:一般为用户名和密码组合的摘要算法
*       ip:192.168.1.1
*       world:开放式权限控制模式，数据节点的访问权限对所有用户开放.world:anyone
*       super:超级用户，可以对zookeeper上的数据节点进行操作。
*连接状态：
* KeeperStat.Expired  在一定时间内客户端没有收到服务器的通知， 则认为当前的会话已经过期了。
* KeeperStat.Disconnected  断开连接的状态
* KeeperStat.SyncConnected  客户端和服务器端在某一个节点上建立连接，并且完成一次version、zxid同步
* KeeperStat.authFailed  授权失败
*
*
*
* */

public class AuthControl implements Watcher{
    private static final String ZKSTRING = "192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";
    private static ZooKeeper zooKeeper = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static CountDownLatch countDownLatch1 = new CountDownLatch(1);


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        new ZooKeeper(ZKSTRING, 5000, new AuthControl());
        countDownLatch.await();

        ACL digest=new ACL(ZooDefs.Perms.CREATE, new Id("digest","root:root"));
        ACL ip=new ACL(ZooDefs.Perms.CREATE, new Id("ip","192.168.1.1"));

        List<ACL> acls=new ArrayList<>();
        acls.add(digest);
        acls.add(ip);
        zooKeeper.create("/auth1","123".getBytes(),acls,CreateMode.PERSISTENT);


        zooKeeper.addAuthInfo("digest","root:root".getBytes());
        zooKeeper.create("/auth","123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);

        ZooKeeper zk=new ZooKeeper(ZKSTRING,5000,new AuthControl());
        countDownLatch.await();
        zk.delete("/auth",-1);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(Event.EventType.None==watchedEvent.getType()&&watchedEvent.getPath()==null){
                countDownLatch1.countDown();
                System.out.println(watchedEvent.getState()+"-->"+watchedEvent.getType());
            }

        }

    }
}