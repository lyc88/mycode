package com.whyuan.order;

//order-api接口的实现类版本1，在这编写下单业务逻辑
public class OrderServiceImpl implements IOrderServices {
    public DoOrderResponse doOrder(DoOrderRequest doOrderRequest) {
        System.out.println("当前处理的下单请求为："+doOrderRequest);
        //响应结果封装
        DoOrderResponse doOrderResponse=new DoOrderResponse();
        doOrderResponse.setCode("状态码：200");
        doOrderResponse.setMessage("处理的消息：您的订单处理成功！");
        doOrderResponse.setData("处理的结果展示：未完成，待续...");
        return doOrderResponse;
    }
}
