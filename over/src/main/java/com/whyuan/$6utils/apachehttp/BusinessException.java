package com.whyuan.$6utils.apachehttp;

import java.util.HashMap;
import java.util.Map;

public class BusinessException extends RuntimeException{
    private String errCode;
    private Object[] params;
    private Map<String, Object> msgMap = new HashMap();

    public BusinessException(String errCode, String msg, Object[] params){
        super(msg);
        this.errCode = errCode;
        this.params = params;
    }

    public BusinessException(String errCode, String msg){
        super(msg);
        this.errCode = errCode;
    }

    public BusinessException(String errCode, String key, String msg, Object[] params){
        super(msg);
        this.errCode = errCode;
        this.msgMap.put(key, msg);
        this.params = params;
    }

    public String getErrCode()
    {
        return this.errCode;
    }

    public void setErrCode(String errCode)
    {
        this.errCode = errCode;
    }

    public Object[] getParams()
    {
        return this.params;
    }

    public void setParams(Object[] params)
    {
        this.params = params;
    }

    public Map<String, Object> getErrFields()
    {
        return this.msgMap;
    }

    public void setErrFields(Map<String, Object> msgMap)
    {
        this.msgMap = msgMap;
    }
}
