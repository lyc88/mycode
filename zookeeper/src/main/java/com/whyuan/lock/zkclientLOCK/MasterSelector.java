package com.whyuan.lock.zkclientLOCK;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Master选举的简单实现（排他锁）
 *
 * 竞选Leader广播自己的数据信息serverid，zxid，Epoch
 * serverid : 在配置server集群的时候，给定服务器的标识id（myid）
   zxid  : 服务器在运行时产生的数据ID， zxid的值越大，表示数据越新
   Epoch: 选举的轮数
 竞选的几个状态
    server的状态：Looking、 Following、Observering、Leading

 */
public class MasterSelector {
    //zkClient客户端
    private ZkClient zkClient;
    //Master注册的节点
    private static final String MASTER_PATH="/MASTER";
    //注册节点内容变化(/MASTER节点存放内容为集群中机器实例)
    private IZkDataListener dataListener;
    //主
    private UserCenter master;
    //从
    private UserCenter slave;

    private boolean isRunning=false;

    //定时任务调度
    ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(1);

    public MasterSelector(UserCenter userCenter,ZkClient zkClient){
        System.out.println("["+userCenter+"] 去争抢master权限");
        this.slave = userCenter;
        this.zkClient=zkClient;

        //内部类实现节点事件监听
        this.dataListener=new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //节点如果被删除, 发起选主操作
                chooseMaster();
            }
        };
    }


    //选举
    private void chooseMaster() {
        if(!isRunning){
            System.out.println("当前服务没有启动");
            return;
        }
        try {
            zkClient.createEphemeral(MASTER_PATH,slave);
            master=slave;
            System.out.println(Thread.currentThread().getName()+"当前选的Master->"+master);

            //匿名内部类实现任务调度
            scheduledExecutorService.schedule(()->{
                //释放锁
                releaseMaster();
            },2, TimeUnit.SECONDS);

        }catch (ZkNodeExistsException e){
            //表示master已经存在
            UserCenter userCenter=zkClient.readData(MASTER_PATH,true);
            if(userCenter==null) {
                //重新进行选举
                chooseMaster();
            }else{
                master=userCenter;
            }

        }

    }

    //模拟因为各种原因，Master宕机
    private void releaseMaster() {
        //判断当前如果是Master就释放锁（故障模拟）
        if(checkIsMaster()){
            zkClient.delete(MASTER_PATH);
        }
    }

    //判断是不是Master
    private boolean checkIsMaster(){
        //判断当前的server是不是master
        UserCenter userCenter=zkClient.readData(MASTER_PATH);
        if(userCenter.getMc_name().equals(slave.getMc_name())){
            master=userCenter;
            return true;
        }
        return false;
    }

    public void start(){
        if(!isRunning){
            isRunning=true;
            //启动服务，开始选举，注册节点数据变化事件
            zkClient.subscribeDataChanges(MASTER_PATH,dataListener);
            chooseMaster();
        }
    }

    public void stop(){
        if(isRunning){
            //停止服务
            isRunning=false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeDataChanges(MASTER_PATH,dataListener);
            releaseMaster();
        }
    }

}
