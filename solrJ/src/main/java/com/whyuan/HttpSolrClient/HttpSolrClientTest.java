package com.whyuan.HttpSolrClient;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*@Author: whyuan
*@Description:
 *
 * CludSolrClient与HttpSolrClient 都继承于SolrClient
 *
 * CloudSolrClient是solrj提供的客户端与solrCloud
 * 该类的实例与zookeeper进行通信来确定solrCloud collections中的solr endpoint
 * 然后使用LBHttpSolrClient发送请求
 * 原文：http://lucene.apache.org/solr/4_5_0/solr-solrj/org/apache/solr/client/solrj/impl/CloudSolrServer.html
 *   难点：CloudSolrClient 源码详解
 *
 *HttpSolrClient 使用了Apache Commons HTTP客户端来连接Solr.
 *
 *
 *
*@Date: Created in 16:42 2017/12/28
*@Modified By:
*/
public class HttpSolrClientTest {
    String url="";

    /**
    *@Author: whyuan
    *@Description: //根据索引：增加或修改
    *@Date: Created in 16:19 2017/12/28
    *@Modified By: 
    */
    public void updateIndex() throws IOException, SolrServerException {
        //CloudSolrClient cloudSolrClient=new CloudSolrClient(url);
        //SolrDocumentList solrDocumentList=new SolrDocumentList();


        HttpSolrClient httpSolrClient=new HttpSolrClient(url);
        SolrInputDocument solrInputDocument=new SolrInputDocument();

        solrInputDocument.addField("id","qaz1");
        solrInputDocument.addField("product_name","SolrJ使用");
        solrInputDocument.addField("product_catalog", "1");
        solrInputDocument.addField("product_catalog_name", "书籍");
        solrInputDocument.addField("product_price", "11");
        solrInputDocument.addField("product_description", "这是一本好书");
        solrInputDocument.addField("product_picture", "图片地址");

        httpSolrClient.add(solrInputDocument);
        httpSolrClient.commit();
        httpSolrClient.close();
    }

    /**
    *@Author: whyuan
    *@Description: 删
    *@Date: Created in 16:23 2017/12/28
    *@Modified By:
    */
    public void deleteIndex() throws IOException, SolrServerException {
        HttpSolrClient httpSolrClient=new HttpSolrClient(url);

        //删除一个
        httpSolrClient.deleteById("qaz1");

        //2.删除多个
        List<String> ids = new ArrayList<String>();
        ids.add("1");
        ids.add("2");
        httpSolrClient.deleteById(ids);

        //3.根据查询条件删除数据,这里的条件只能有一个，不能以逗号相隔
        httpSolrClient.deleteByQuery("id:zxj1");

        //一定要记得提交，否则不起作用
        httpSolrClient.commit();
        httpSolrClient.close();


    }

    /**
    *@Author: whyuan
    *@Description: 查
    *@Date: Created in 16:24 2017/12/28
    *@Modified By:
    */
    public void IndexSearch() throws IOException, SolrServerException {
        HttpSolrClient httpSolrClient = new  HttpSolrClient(url);

        httpSolrClient.setConnectionTimeout(30000);
        httpSolrClient.setDefaultMaxConnectionsPerHost(100);
        httpSolrClient.setMaxTotalConnections(100);
        httpSolrClient.setSoTimeout(30000);

        //创建查询对象
        SolrQuery solrQuery=new SolrQuery();
        //q 查询字符串，如果查询所有*:*
        solrQuery.set("q","product_name:小黄人");
        //fq 过滤条件，过滤是基于查询结果中的过滤
        solrQuery.set("fq", "product_catalog_name:幽默杂货");
        //sort 排序，请注意，如果一个字段没有被索引，那么它是无法排序的
        solrQuery.set("sort", "product_price desc");
        //start：起始偏移量。rows：每页文档数量
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        //fl 查询哪些结果出来，不写的话，就查询全部。
        solrQuery.set("fl", "");
        //df 默认搜索的域
        solrQuery.set("df", "product_keywords");

        //======高亮设置===
        //开启高亮
        solrQuery.setHighlight(true);
        //高亮域
        solrQuery.addHighlightField("product_name");
        //前缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        //后缀
        solrQuery.setHighlightSimplePost("</span>");

        //执行搜索
        QueryResponse queryResponse = httpSolrClient.query(solrQuery);

        //搜索结果
        SolrDocumentList results = queryResponse.getResults();
        //查询出来的数量
        long numFound = results.getNumFound();
        System.out.println("总查询出:" + numFound + "条记录");

        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        //遍历搜索记录,获取高亮信息
        for (SolrDocument solrDocument : results) {
            System.out.println("商品id:" + solrDocument.get("id"));
            System.out.println("商品名称 :" + solrDocument.get("product_name"));
            System.out.println("商品分类:" + solrDocument.get("product_catalog"));
            System.out.println("商品分类名称:" + solrDocument.get("product_catalog_name"));
            System.out.println("商品价格:" + solrDocument.get("product_price"));
            System.out.println("商品描述:" + solrDocument.get("product_description"));
            System.out.println("商品图片:" + solrDocument.get("product_picture"));

            //输出高亮
            Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
            List<String> list = map.get("product_name");
            if(list != null && list.size() > 0){
                System.out.println(list.get(0));
            }
        }
        httpSolrClient.close();

    }

    public static void main(String[] args) {

    }

}
