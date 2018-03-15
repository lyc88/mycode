package com.whyuan.$6utils.log;

import com.google.common.base.Strings;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.schema.SchemaRepresentation;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Author: huyisen@gmail.com
 * <p>Date: 2017-07-12 11:28
 * <p>Version: 1.0
 */
public class SolrUtils {


    private static final Logger logger = LoggerFactory.getLogger(SolrUtils.class);


    public static Schema getSchema(CloudSolrClient client) {

        if (Strings.isNullOrEmpty(client.getDefaultCollection()))
            throw new IllegalArgumentException("必须设置 DefaultCollection 值。");

        SchemaRequest request = new SchemaRequest();
        SchemaResponse response = new SchemaResponse();
        try {
            response.setResponse(client.request(request));
        } catch (Exception e) {
            logger.error("[{}] 集群初始化 Collection={}. 失败!", client.getZkHost(), client.getDefaultCollection(), e);
        }

        SchemaRepresentation $schema = response.getSchemaRepresentation();

        Set<FieldType> fileTypeSet = new HashSet<>();
        Set<Field> fieldSet = new HashSet<>();

        fileTypeSet.addAll($schema.getFieldTypes().stream()
                .map(f -> new FieldType(f.getAttributes().get("name").toString()))
                .collect(Collectors.toList()));

        fieldSet.addAll($schema.getFields().stream()
                .map(f -> new Field(f.get("name").toString(), f.get("type").toString()))
                .collect(Collectors.toList()));

        return new Schema(fileTypeSet, fieldSet);
    }


}
