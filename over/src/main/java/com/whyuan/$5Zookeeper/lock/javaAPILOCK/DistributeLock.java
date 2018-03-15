package com.whyuan.$5Zookeeper.lock.javaAPILOCK;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *分布式锁的简单实现
 *
 *
 */
public class DistributeLock {

    //根节点
    private static final String ROOT_LOCKS="/LOCKS";

    private ZooKeeper zooKeeper;

    private int sessionTimeOut;

    private String lockID;

    private static final byte[] DATA={1,2,3};

    private static final CountDownLatch COUNT_DOWN_LATCH=new CountDownLatch(1);

    public DistributeLock() throws IOException, InterruptedException {
        this.zooKeeper = ZookerClient.getInstance();
        this.sessionTimeOut = ZookerClient.getSessionTimeOut();
    }


    // 获取锁
    //创建临时序列节点->观测相邻节点是否被删除
    public boolean lock(){
        try {
            // 创建临时，顺序节点
            lockID=zooKeeper.create(ROOT_LOCKS+"/",DATA, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName()+"->成功创建了lock节点["+lockID+"], 开始去竞争锁");
            //获取根节点下的所有子节点
            List<String> childrenNodes=zooKeeper.getChildren(ROOT_LOCKS,true);
            //排序集合
            SortedSet<String> sortedSet=new TreeSet<String>();
            for (String children:childrenNodes){
                sortedSet.add(ROOT_LOCKS+"/"+children);
            }
            //集合中最小节点
            String first=sortedSet.first();
            if(first.equals(lockID)){
                //表示当前就是最小的节点
                System.out.println(Thread.currentThread().getName()+"->成功获得锁，lock节点为:["+lockID+"]");
                return true;
            }
            //比当前最小节点还小的其他节点集合
            SortedSet<String> lessThanLockId=sortedSet.headSet(lockID);
            if(!lessThanLockId.isEmpty()){
                //拿到比当前lockID这个节点更小的上一个节点
                String prevLockID=lessThanLockId.last();
                zooKeeper.exists(prevLockID,new LockWatcher(COUNT_DOWN_LATCH));
                COUNT_DOWN_LATCH.await(sessionTimeOut, TimeUnit.MILLISECONDS);
                //上面这段代码意味着如果会话超时或者节点被删除（释放）了
                System.out.println(Thread.currentThread().getName()+" 成功获取锁：["+lockID+"]");
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //释放锁
    public boolean unlock(){
        System.out.println(Thread.currentThread().getName()+"->开始释放锁:["+lockID+"]");
        try {
            zooKeeper.delete(lockID,-1);
            System.out.println("节点["+lockID+"]成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }
}
