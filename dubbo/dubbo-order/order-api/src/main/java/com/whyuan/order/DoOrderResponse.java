package com.whyuan.order;

import java.io.Serializable;
/*
* 模拟订单请求响应结果封装
*
* */
public class DoOrderResponse implements Serializable {
    private static final long serialVersionUID = 1820403580720850727L;

    private Object data;

    private String code;

    private String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DoOrderResponse{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
