package com.urun.advicer;

import com.google.gson.JsonSyntaxException;
import com.urun.exception.ErrorInfoInterface;
import com.urun.exception.GlobalErrorInfoException;
import com.urun.exception.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
*@Author: whyuan
*@Description: 全局异常处理
*@Date: Created in 12:21 2017/12/21
*@Modified By:
*/
@RestControllerAdvice(basePackages = "com.urun.controller")
public class RestControllerAdvicer {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerAdvicer.class);

    @ExceptionHandler(value = {GlobalErrorInfoException.class})
    public Object handleBaseException( GlobalErrorInfoException exception) {
        ErrorInfoInterface errorInfo = exception.getErrorInfo();
        ResultBody result = new ResultBody(errorInfo);
        logger.info("异常处理：{Code}：{},{Message}：{},{Result}：{}",result.getCode(),result.getMessage()/*,result.getResult()*/);

        return result;
    }
}
