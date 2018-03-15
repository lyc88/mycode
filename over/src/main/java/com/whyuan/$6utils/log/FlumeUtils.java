package com.whyuan.$6utils.log;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static org.apache.solr.util.SolrCLI.getHttpClient;

/**
 * 发数据到Flume的测试单元。
 * <p>
 * <p>Author: huyisen@gmail.com
 * <p>Date: 2017-01-03 11:58
 * <p>Version: 1.0
 */
public class FlumeUtils {

    private final static Logger logger = LoggerFactory.getLogger(FlumeUtils.class);

    public void test() throws Exception {
        long timestamp = System.currentTimeMillis();

        Map<String, Object> headers = new HashMap<>();
        headers.put("topic", "test");
        headers.put("timestamp", timestamp);

        List<Map<String, Object>> datas = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> $data = new HashMap<>();
            $data.put("ID", "1234567890" + i);
            $data.put("Title", "我是标题-" + i);
            $data.put("Content", "我是内容-" + i);
            $data.put("Time", timestamp);
            datas.add($data);
        }

        byte[] jdata = buildFlumeData(headers, datas, false);
        HttpPost httpPost = new HttpPost("http://119.4.208.77:10065");
        httpPost.setEntity(new ByteArrayEntity(jdata, ContentType.APPLICATION_JSON));
        execute(httpPost);


    }

    public static byte[] buildFlumeData(Map<String, Object> headers, List<Map<String, Object>> datas) throws IOException {
        return buildFlumeData(headers, datas, false);
    }

    public static byte[] buildFlumeData(Map<String, Object> headers, List<Map<String, Object>> datas, boolean isCompress) throws IOException {

        if (headers == null || headers.isEmpty())
            throw new IllegalArgumentException("headers is null or empty.");
        if (headers.get("topic") == null || "".equals(headers.get("topic")))
            throw new IllegalArgumentException("headers.topic is null or empty.");
        if (datas == null || datas.isEmpty())
            throw new IllegalArgumentException("data is null or empty.");

        List<Object> ldata = new ArrayList<>();
        for (Map<String, Object> data : datas) {
            Map<String, Object> $data = new HashMap<>();
            $data.put("headers", new HashMap<>(headers));
            $data.put("body", JSON.toJSONString(data));
            ldata.add($data);
        }
        String rs = JSON.toJSONString(ldata);
        return isCompress ? compress(rs) : rs.getBytes("UTF-8");
    }

    //压缩
    private static byte[] compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        return out.toByteArray();
    }

    // 解压缩
    private static byte[] uncompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return bytes;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String execute(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        String result;

        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    return result;
                }
            }
        } catch (IOException e) {
            logger.error("" + e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }
}
