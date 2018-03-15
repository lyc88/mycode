package com.whyuan.api.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;
/*
* 永久封装了watcher
* */
public class ZkClientAPI {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";

    private static ZkClient getInstance(){
        //1.建立会话
        return new ZkClient(ZKSTRING,10000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient=getInstance();
        System.out.println(zkClient+" - > success");

        //2.zkclient 提供递归创建父节点的功能
        zkClient.createPersistent("/zkclient/zkclient1/zkclient1-1/zkclient1-1-1",true);
        System.out.println("success");

        //3.删除节点
        zkClient.deleteRecursive("/zkclient");

        //4.获取子节点
        List<String> list=zkClient.getChildren("/node");
        System.out.println(list);


        //5.注册节点变化观察者，订阅子节点变化事件subscribeChildChanges
        zkClient.subscribeChildChanges("/node", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

            }
        });

        //6.注册数据变化观察者，订阅数据变化事件subscribeDataChanges
        zkClient.subscribeDataChanges("/node", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+"->节点修改后的值"+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("节点数据被删除触发");
            }
        });


        //7.往节点写数据
        zkClient.writeData("/node","node");
        TimeUnit.SECONDS.sleep(2);

    }
}
