package com.whyuan.CloudSolrClient;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.cloud.ClusterState;
import org.apache.solr.common.cloud.ZkStateReader;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CloudSolrClientTest {
    public static void main(String[] args) throws IOException, SolrServerException {
        String zkHost="";
        String collectionName="";
        CloudSolrClient cloudSolrClient=new CloudSolrClient(zkHost);
        cloudSolrClient.setZkClientTimeout(30000);
        cloudSolrClient.setZkConnectTimeout(50000);
        cloudSolrClient.setDefaultCollection(collectionName);

        //连接到zk的整体。这是一种可选的方法，可用于在任何其他请求发送之前强制连接。
        cloudSolrClient.connect();

        ZkStateReader zkStateReader=cloudSolrClient.getZkStateReader();
        ClusterState clusterState=zkStateReader.getClusterState();

        //Solr需要的查询参数
        ModifiableSolrParams query = new ModifiableSolrParams();
        //获取传到zookeeper的集合
        Set<String> collections=clusterState.getCollections();

        /*Iterator<String> iterator=collections.iterator();
        while (iterator.hasNext()){
            String collection=iterator.next();
            QueryResponse queryResponse= cloudSolrClient.query(collection,query, SolrRequest.METHOD.POST);
            SolrDocumentList documentList=queryResponse.getResults();

            //聚合操作拿到文档的ID集合
            List<String> ids = documentList.stream()
                    .map(document -> String.valueOf(document.get("ID")))
                    .collect(Collectors.toList());
        }*/
        //Java8 遍历
        collections.stream()
                .forEach(collection ->{
                            try {
                                QueryResponse queryResponse= cloudSolrClient.query(collection,query, SolrRequest.METHOD.POST);
                                SolrDocumentList documentList=queryResponse.getResults();

                                //聚合操作拿到文档的ID集合
                                List<String> ids = documentList.stream()
                                        .map(document -> String.valueOf(document.get("ID")))
                                        .collect(Collectors.toList());
                            } catch (SolrServerException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                );
        //增删改查操作和HttpSolrClient一样






        cloudSolrClient.close();



    }

    public SolrClient solrClient;

    public SolrClient createCloudSolrClient(String zkHost,String collectionName){
        CloudSolrClient cloudSolrClient=new CloudSolrClient(zkHost);
        cloudSolrClient.setZkClientTimeout(30000);
        cloudSolrClient.setZkConnectTimeout(50000);
        cloudSolrClient.setDefaultCollection(collectionName);
        return cloudSolrClient;
    }

    public void close() {
        try {
            solrClient.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
