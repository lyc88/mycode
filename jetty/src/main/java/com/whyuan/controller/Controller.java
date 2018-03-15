package com.whyuan.controller;


import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
*@Author: whyuan
*@Description:客户在访问目标资源之前，对访问的请求，响应做拦截。
 * web浏览器------》web服务器-----》过滤器-----》web资源（jsp Servlet html等）
 * web浏览器《------web服务器《-----过滤器《-----web资源（jsp Servlet html等）
 *实现URL级别的权限访问控制、过滤敏感词汇、压缩响应信息等一些高级功能。
 *
*@Date: Created in 11:53 2017/12/28
*@Modified By:
*/
public class Controller implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final String HOST_NAME;//服务器主机名
    private static ImmutableSet actionNameSet;//请求的集合

    //获取启动服务的机器地址
    static {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            System.out.println(Controller.class+"{inetAddress}："+inetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error("UnknownHostException =" + e);
        } finally {
            String host = inetAddress.getHostName();
            HOST_NAME = host.contains(".") ? host.substring(0, host.indexOf(".")) : host;
            System.out.println(Controller.class+"{HOST_NAME}："+HOST_NAME);
        }
        logger.info("{inetAddress}：{},{HOST_NAME}：{}",inetAddress,HOST_NAME);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //TODO 服务初始化工作，一般用来实例的注入（请求名实例化，Mysql参数,业务参数等，可IOC注入）
        System.out.println(Controller.class+"过滤器初始化（web服务启动的时候创建Filter实例，并进行初始化.....）");
        ImmutableSet.Builder<String> $actionNameSet=ImmutableSet.builder();

        for(Action.ActionName actionName: Action.ActionName.values()){
            $actionNameSet.add(actionName.name().toLowerCase());
        }
        actionNameSet=$actionNameSet.build();
        logger.info("{actionNameSet}：{}",actionNameSet);
        System.out.println(Controller.class+"可接受的请求类型为："+actionNameSet);





    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //TODO 请求处理:url:localhost:8888/web/test/common/*
        System.out.println(Controller.class+"开始请求处理（作用在Servlet service方法之前）");

        //只处理Http协议请求：
        if(!(servletRequest instanceof HttpServletRequest)) filterChain.doFilter(servletRequest,servletResponse);

        //请求，响应类型转化为 HttpServletRequest，HttpServletResponse
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //客户端请求编码
        request.setCharacterEncoding("UTF-8");
        //设置响应类型，及编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        //上下文路径：测试路径为：/web/test
        String contextPath=request.getContextPath();
        //过滤器作用路径：/common/*
        String servletPath=request.getServletPath();
        //获取该请求类型
        String action=getAction(servletPath);
        //获取请求参数
        request.getParameterMap().entrySet().stream()
                .filter($ -> $.getValue() != null &&
                        $.getValue().length > 0 &&
                        !Strings.isNullOrEmpty($.getValue()[0]))
                .forEach($ -> {
                            System.out.println("请求参数key："+$.getKey()+"请求参数value："+$.getValue()[0]);
                        });
        //获取浏览器代理
        String ua=request.getHeader("User-Agent");
        String uri=request.getRequestURI();
        String url=request.getRequestURL().toString();

        System.out.println(Controller.class+"{URl}："+url);
        System.out.println(Controller.class+"{URI}："+uri);
        System.out.println(Controller.class+"{contextPath}："+contextPath);
        System.out.println(Controller.class+"{servletPath}："+servletPath);
        System.out.println(Controller.class+"{action}："+action);
        System.out.println(Controller.class+"{User-Agent}："+ua);

        logger.info("{contextPath}：{},{servletPath}：{},{action}：{}",contextPath,servletPath,action);


        //只处理select,add,update,delete请求。
        if(!Strings.isNullOrEmpty(action)&& actionNameSet.contains(action)){
            switch (action){
                case "select":
                    System.out.println("执行Select逻辑");
                    break;
                case "add":
                    System.out.println("执行add逻辑");
                    break;
                case "update":
                    System.out.println("执行update逻辑");
                    break;
                case "delete":
                    System.out.println("执行delete逻辑");
                    break;
            }
        }


        //调用该方法，则web服务器就会调用web资源的service方 法，即web资源就会被访问，否则web资源不会被访问。
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println(Controller.class+"过滤器放行（请求处理完成，允许访问web资源）");
    }

    @Override
    public void destroy() {

    }

    /**
    *@Author: whyuan
    *@Description: 获取请求类型
    *@Date: Created in 11:06 2017/12/29
    *@Modified By:
    */
    private String getAction(String servletPath) {
        return (servletPath.contains("?")) ?
                servletPath.substring(servletPath.lastIndexOf("/") + 1, servletPath.indexOf("?")) :
                servletPath.substring(servletPath.lastIndexOf("/") + 1);
    }
}
