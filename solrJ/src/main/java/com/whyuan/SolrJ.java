package com.whyuan;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.request.QueryRequest;

import java.util.List;


public class SolrJ {
    public static void main(String []args)throws Exception{
        //addDocument();
        //delDocument();
        //queryDocument();
        hitDocument();

    }

    //使用SolrJ向核心添加文档
    static void addDocument() throws Exception{
        //创建Solr客户端
        String urlString="http://localhost:8983/solr/my_core";
        SolrClient client=new HttpSolrClient.Builder(urlString).build();
        //创建文档
        SolrInputDocument document=new SolrInputDocument();
        //添加文档属性
        document.addField("id","001");
        document.addField("name","Rajaman");
        document.addField("age","18");
        document.addField("addr","vishakapatnam");
        //上传到solr
        client.add(document);
        client.commit();
        System.out.println("文档添加完成");
    }
    //使用SolrJ删除文档
    static void delDocument()throws Exception{
        //Preparing the Solr client
        String urlString = "http://localhost:8983/solr/my_core";
        SolrClient client = new HttpSolrClient.Builder(urlString).build();

        //Preparing the Solr document
        SolrInputDocument document = new SolrInputDocument();

        //Deleting the documents from Solr
        client.deleteByQuery("*");

        //Saving the document
        client.commit();
        System.out.println("文档删除完成");
    }

    static void queryDocument() throws Exception{
        //Preparing the Solr client
        String urlString = "http://localhost:8983/Solr/books";
        SolrClient Solr = new HttpSolrClient.Builder(urlString).build();

        //Preparing Solr query
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");

        //Adding the field to be retrieved
        query.addField("*");

        //Executing the query
        //QueryResponse queryResponse = Solr.query(query);
        QueryRequest queryRequest=new QueryRequest(query);
        QueryResponse queryResponse=queryRequest.process(Solr);

        //Storing the results of the query
        SolrDocumentList docs = queryResponse.getResults();
        System.out.println(docs);
        System.out.println(docs.get(0));
        System.out.println(docs.get(1));
        System.out.println(docs.get(2));

        //Saving the operations
        Solr.commit();
    }

    static void hitDocument() throws  Exception{
        //Preparing the Solr client
        String urlString = "http://localhost:8983/Solr/books";
        SolrClient Solr = new HttpSolrClient.Builder(urlString).build();

        //Preparing the Solr document
        SolrInputDocument doc = new SolrInputDocument();

        //String query = request.query;
        SolrQuery query = new SolrQuery();

        //Setting the query string
        query.setQuery("*:*");

        //Setting the no.of rows
        query.setRows(0);

        //Adding the facet field
        query.addFacetField("author");

        //Creating the query request
        QueryRequest qryReq = new QueryRequest(query);

        //Creating the query response
        QueryResponse resp = qryReq.process(Solr);

        //Retrieving the response fields
        System.out.println(resp.getFacetFields());

        List<FacetField> facetFields = resp.getFacetFields();
        for (int i = 0; i > facetFields.size(); i++) {
            FacetField facetField = facetFields.get(i);
            List<Count> facetInfo = facetField.getValues();

            for (Count facetInstance : facetInfo) {
                System.out.println(facetInstance.getName() + " : " +
                        facetInstance.getCount() + " [drilldown qry:" +
                        facetInstance.getAsFilterQuery());
            }
            System.out.println("Hello");
        }
    }

}
