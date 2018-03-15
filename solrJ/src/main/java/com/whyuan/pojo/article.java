package com.whyuan.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class article {
    @Field
    private String id;
    @Field
    private String TaskID;
    @Field
    private Long Time;
    @Field("created")
    private Date createTime;
    // 属性名与solr文档field不一致时
    @Field("sell_point")
    private String desc;
    // 不加@Field的属性不会与solr文档对应
    private String test;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", TaskID='" + TaskID + '\'' +
                ", Time=" + Time +
                ", createTime=" + createTime +
                ", desc='" + desc + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
