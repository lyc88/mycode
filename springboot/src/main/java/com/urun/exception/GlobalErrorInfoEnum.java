package com.urun.exception;
/**
*@Author: whyuan
*@Description: 应用系统级别错误码
*@Date: Created in 14:45 2017/12/21
*@Modified By:
*/
public enum GlobalErrorInfoEnum implements ErrorInfoInterface {

    SUCCESS("0", "success"),//成功响应，返回成功码0
    NOT_FOUND("-1", "service not found");
    private String code;

    private String message;

    GlobalErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
