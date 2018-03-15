package com.whyuan.order;

import java.util.concurrent.TimeUnit;

//order-api接口的实现类，在这编写下单业务逻辑
//模拟版本2的实现
public class OrderServiceImpl2 implements IOrderServices {
    public DoOrderResponse doOrder(DoOrderRequest doOrderRequest) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("当前处理的下单请求为："+doOrderRequest);
        //响应结果封装
        DoOrderResponse doOrderResponse=new DoOrderResponse();
        doOrderResponse.setCode("状态码：200");
        doOrderResponse.setMessage("处理的消息：您的订单处理成功！");
        doOrderResponse.setData("处理的结果展示版本2：未完成，待续...");
        return doOrderResponse;
    }
}
