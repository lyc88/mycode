package com.urun.constant;
import com.urun.exception.ErrorInfoInterface;


/**
*@Author: whyuan
*@Description: 业务错误码：参数错误
*@Date: Created in 14:51 2017/12/21
*@Modified By:
*/
public enum ParamErrorInfoEnum implements ErrorInfoInterface {
    PARAMS_ERROR("000001","参数错误"),
    PARAMS_NO_FOUND("000002","参数未找到");

    private String code;

    private String message;

    ParamErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
