package com.whyuan.$6utils.zk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;


public class ZkUtils {

    public static final String kafkaServices(String zkhost) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper(zkhost, 30000, (e) -> {});

        String path = "/brokers/ids";
        List<String> children = zk.getChildren(path, false);

        StringBuffer sb = new StringBuffer();
        for (String child : children) {
            byte[] data = zk.getData(path + "/" + child, false, new Stat());

            JSONObject o = JSON.parseObject(Bytes.toString(data));
            sb.append(o.getString("host") + ":" + o.getString("port")+",");
        }

        return sb.toString();
    }
}
