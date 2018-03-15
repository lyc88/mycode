package com.whyuan.lock.zkclientLOCK;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * 模拟多个客户端去争选Master
 * 通过注册临时节点，监听节点数据变更去操作
 *
 */
public class TestMasterSelecter {
    private static final String ZKSTRING="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";
    private static int sessionTimeOut= 5000;
    private static int connectionTimeOut=5000;

    public static void main(String[] args) throws InterruptedException {

        List<MasterSelector> selectorLists=new ArrayList<>();
        for(int i=0;i<10;i++) {
            ZkClient zkClient=new ZkClient(ZKSTRING,sessionTimeOut,connectionTimeOut,new SerializableSerializer());
            UserCenter userCenter=new UserCenter(i,"客户端"+i);
            MasterSelector masterSelector = new MasterSelector(userCenter,zkClient);

            masterSelector.start();
            TimeUnit.SECONDS.sleep(3);
        }
        for(MasterSelector selector:selectorLists){
            //模拟3s后宕机
            selector.stop();
        }
    }
}
