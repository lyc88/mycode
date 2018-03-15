package com.whyuan.order;

import java.io.Serializable;
/*
*模拟订单请求处理
*
* */
public class DoOrderRequest implements Serializable {
    private static final long serialVersionUID = -6714804388915595615L;

    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DoOrderRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
