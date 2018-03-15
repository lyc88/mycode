package com.whyuan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;

//HBase全表(范围)扫描
public class Demo02 {
    public static void main(String[] args) throws IOException {
        String zkHost="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";
        //创建到表的连接
        Configuration configuration= HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zkHost);
        HTable hTable=new HTable(configuration,"tab1".getBytes());

        //2.创建Scan对象
        Scan scan=new Scan();
        //范围扫描
        scan.setStartRow("rk1".getBytes());
        scan.setStopRow("rk3".getBytes());

        //3.根据scan查询数据，得到结果集
        ResultScanner resultScanner=hTable.getScanner(scan);
        //4.结果集遍历
        Iterator<Result> iterator=resultScanner.iterator();
        StringBuffer sb=new StringBuffer();
        while(iterator.hasNext()){
            Result result= iterator.next();
            //获取行键
            String rk=new String(result.getRow());
            NavigableMap<byte[],NavigableMap<byte[],NavigableMap<Long,byte[]>>> map=result.getMap();
            for(Map.Entry<byte[],NavigableMap<byte[],NavigableMap<Long,byte[]>>> entry:map.entrySet()){
                //列簇
                String cf=new String(entry.getKey());
                NavigableMap<byte[],NavigableMap<Long,byte[]>> cmap=entry.getValue();
                for(Map.Entry<byte[],NavigableMap<Long,byte[]>> centry:cmap.entrySet()){
                    //列
                    String c=new String(centry.getKey());
                    //最新版本的值
                    String v=new String(centry.getValue().firstEntry().getValue());
                    sb.append(rk+"="+cf+"-"+c+"-"+v+" ");
                }
            }
            sb.append("\r\n");
        }
        System.out.println(sb.toString());
    }
}
