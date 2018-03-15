package com.urun.controller;

import com.google.common.base.Strings;
import com.google.gson.JsonSyntaxException;
import com.urun.constant.ParamErrorInfoEnum;
import com.urun.exception.GlobalErrorInfoException;
import com.urun.exception.ResultBody;
import com.urun.utils.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class UrunRestConrtoller{
    private static final Logger logger = LoggerFactory.getLogger(UrunRestConrtoller.class);

    private static AtomicInteger atomicInteger=new AtomicInteger(0);

    @RequestMapping(value = "index")
    public ResultBody index(@RequestBody String jsonData
           /* @RequestParam(value ="jsonData",required = true) String jsonData*/)
            throws GlobalErrorInfoException {
        RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes)requestAttributes;
        HttpServletRequest request=servletRequestAttributes.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        logger.info("第"+atomicInteger.getAndIncrement()+"次访问该接口@"+"开始处理请求@{url}: {}, {method}: {}, {uri}: {}, {params}: {}", url, method, uri, queryString);

        Object obj="";
        if(!Strings.isNullOrEmpty(jsonData)){
            try {
                obj=GsonUtil.parseJsonToObject(jsonData,Object.class);
            }catch (JsonSyntaxException e){
                throw new JsonSyntaxException("请正确填写Json字符串！");
            }
        }else {
            throw new GlobalErrorInfoException(ParamErrorInfoEnum.PARAMS_NO_FOUND);
        }
        return new ResultBody(obj);
    }


    //===============================异常测试：http://localhost:8888/npe===================================
    //该异常将被RestControllerAdvicer中@ExceptionHandler处理
    @RequestMapping("/npe")
    public String npe()throws Exception{
        System.out.println("访问npe");
        throw new GlobalErrorInfoException(ParamErrorInfoEnum.PARAMS_ERROR);
    }
}
