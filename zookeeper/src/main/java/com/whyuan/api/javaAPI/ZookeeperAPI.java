package com.whyuan.api.javaAPI;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
/**
 * Zookeeper原生API操作
 * 连接状态
 * 节点操作
 * 数据操作
 * 观察者：一次性触发，事件被处理一次后，会被移除。若要永久监听，需反复注册。
 *
 *
 *连接状态
 KeeperStat.Expired  在一定时间内客户端没有收到服务器的通知， 则认为当前的会话已经过期了。
 KeeperStat.Disconnected  断开连接的状态
 KeeperStat.SyncConnected  客户端和服务器端在某一个节点上建立连接，并且完成一次version、zxid同步
 KeeperStat.authFailed  授权失败
 *
 *
 * 事件类型
 NodeCreated  当节点被创建的时候，触发
 NodeChildrenChanged  表示子节点被创建、被删除、子节点数据发生变化
 NodeDataChanged    节点数据发生变化
 NodeDeleted        节点被删除
 None   客户端和服务器端连接状态发生变化的时候（建立连接），事件类型就是None

 *
 *  * 参数说明：
 connectString -- host:port[，host:port][basePath] 指定的服务器列表，多个host:port之间用英文逗号分隔。还可以可选择的指定一个基路径，如果指定了一个基路径，则所有后续操作基于这个基路径进行。
 sessionTimeOut -- 会话超时时间。以毫秒为单位。客户端和服务器端之间的连接通过心跳包进行维系，如果心跳包超过这个指定时间则认为会话超时失效。
 watcher -- 指定默认观察者。如果为null表示不需要观察者。
 canBeReadOnly -- 是否支持只读服务。只当一个服务器失去过半连接后不能再进行写入操作时，是否继续支持读取操作。略
 sessionId、SessionPassword -- 会话编号 会话密码，用来实现会话恢复。

 **注意，整个创建会话的过程是异步的，构造方法会在初始化连接后即返回，并不代表真正建立好了一个会话，此时会话处于"CONNECTING"状态。
 **当会话真正创建起来后，服务器会发送事件通知给客户端，只有客户端获取到这个通知后，会话才真正建立。
 */
public class ZookeeperAPI {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";
    private static ZooKeeper zooKeeper=null;
    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //1.创建会话，指定默认观察者，当执行事务方法有观察者参数为true，该方法调用默认观察者
            zooKeeper=new ZooKeeper(ZKSTRING, 5000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //2.异步连接状态
                //会话生命周期：NOT_CONNECTED->CONNECTING->CONNECTED->CLOSED
                if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                    //第一次连接进来
                    if(Event.EventType.None==watchedEvent.getType()&&watchedEvent.getPath()==null){
                        countDownLatch.countDown();
                        System.out.println("Watcher观察到的当前连接状态为："+watchedEvent.getState());
                    }
                    //3.节点数据变化状态
                    if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
                        String path = watchedEvent.getPath();
                        System.out.println("节点数据发生了变化！！路径为："+path);
                        byte[] data = new byte[1024];
                        try {
                            //4.获取节点数据
                            data = zooKeeper.getData(path, true, new Stat());
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String str = new String(data);
                        System.out.println("变化后的数据是："+str);
                    }

                    //5.节点创建或删除状态
                    if(watchedEvent.getType() == Event.EventType.NodeCreated || watchedEvent.getType() == Event.EventType.NodeDeleted){
                        String path = watchedEvent.getPath();
                        Stat stat = null;
                        try {
                            stat = zooKeeper.exists(path, true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("创建或删除节点触发，返回状态码："+stat);
                    }
                }



            }
        });
        countDownLatch.await();//当程序计数器减为0程序放行。
        System.out.println("放行后zookeeper连接状态为："+zooKeeper.getState());

        //6.同步创建节点，创建成功返回创建的路径 CreateMode.EPHEMERAL（临时） CreateMode.PERSISTENT（持久）
        //临时节点下不能挂子节点ephemeral
        String path=zooKeeper.create("/whyuan","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
        System.out.println("临时节点创建成功，返回创建的路径："+path);

        //7.判断是否存在节点。
        Stat stat1=zooKeeper.exists("/node/node1",true);
        if(stat1==null){
            //创建跟节点和子节点
            zooKeeper.create("/node","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            zooKeeper.create("/node/node1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
        if(zooKeeper.exists("/testdelete",true)==null){
            zooKeeper.create("/testdelete","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            //8.删除节点
            zooKeeper.delete("/testdelete",-1);
            System.out.println("删除成功！");
        }

        //修改数据,版本号-1表示不管版本
        Stat stat=zooKeeper.setData("/node/node1","456".getBytes(),-1);
        System.out.println("修改节点数据成功返回的状态码："+stat);

        //获取数据 - 获取子节点
		List<String> list = zooKeeper.getChildren("/", new ChildWatcher());
		for(String s : list){
			System.out.println("根节点/挂载了："+s);
		}

        //使程序不结束，有机会异步执行回调函数
        while(true){}
    }

    //注册观察者监测节点变化
    static class ChildWatcher implements Watcher{

        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getType()== Event.EventType.NodeChildrenChanged){
                try {
                    List<String> list=zooKeeper.getChildren("/",new ChildWatcher());
                    list.forEach(s->{
                        System.out.println(s);
                    });
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
