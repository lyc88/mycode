package com.whyuan;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;


public class HbaseDemo {
    private String zkHost="192.168.158.130:2181,192.168.158.131:2181,192.168.158.132:2181";
    private Connection connection;
    private Table table;
    private Configuration config;

    public static void main(String[] args) throws IOException {
        HbaseDemo hbaseDemo=new HbaseDemo();

        hbaseDemo.initConnection();//连接

        hbaseDemo.createTable();//创建表

        Table testtable=hbaseDemo.getHbaseTable("testtable");//获得表
        System.out.println(testtable);

    }

    //创建连接
    public void initConnection() {

        if (connection != null && !connection.isClosed())
            return;
        Configuration config = HBaseConfiguration.create();

        config.set("hbase.zookeeper.quorum", zkHost.substring(0, zkHost.lastIndexOf(":")));
        config.set("zookeeper.znode.parent", "/hbase-unsecure");
        config.set("hbase.client.pause", "2000");
        config.set("hbase.client.retries.number", "10");
        config.set("hbase.rpc.timeout", "20000");

        try {
            connection = ConnectionFactory.createConnection(config);
            //使用传递的conf实例创建一个新的Connection 实例。
            // 连接封装了与集群的连接的所有内务管理。从返回的连接共享zookeeper连接，元缓存以及到区域服务器和主设备的连接创建的所有表和接口。
            //调用者负责调用Connection.close()返回的连接实例
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建表
    public void createTable() throws IOException {
        config=HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zkHost);
        //管理员
        HBaseAdmin admin=new HBaseAdmin(config);

        //表名描述器
        TableName tableName=TableName.valueOf("testtable");
        //表描述器
        HTableDescriptor hTableDescriptor=new HTableDescriptor(tableName);
        //列簇描述器
        HColumnDescriptor hColumnDescriptor=new HColumnDescriptor("cf1".getBytes());
        //表关联列簇
        hTableDescriptor.addFamily(hColumnDescriptor);
        //管理员创建表
        admin.createTable(hTableDescriptor);
        admin.close();
    }

    //获得HBase表
    public Table getHbaseTable(String tableName){
        try {
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;

    }

    //关闭Hbase连接
    private void closeHbaseConnect(){
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
