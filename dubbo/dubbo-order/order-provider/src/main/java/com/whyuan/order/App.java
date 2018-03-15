package com.whyuan.order;

import com.alibaba.dubbo.container.Main;

/**
 *必须在resources->META-INF->spring下
 * 配置order-provider.xml（服务发布文件）
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //将参数args：此处是spring,加入dubbo容器container.
        Main.main(args);
    }
}
