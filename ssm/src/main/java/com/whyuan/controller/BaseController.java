package com.whyuan.controller;

import com.whyuan.controller.support.ResponseData;
import com.whyuan.controller.support.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    public Logger logger= LoggerFactory.getLogger(BaseController.class);


    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }

    protected ResponseData setEnumResult(ResponseEnum anEnum, Object data){
        ResponseData res =new ResponseData();
        res.setStatus(anEnum.getCode());
        res.setData(data);
        res.setMessage(anEnum.getMsg());
        return res;
    }
}
