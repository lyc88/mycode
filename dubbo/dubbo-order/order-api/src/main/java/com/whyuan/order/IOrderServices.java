package com.whyuan.order;
/*
* 服务提供方Provider:
* 模拟提供订单服务的接口
*
* */
public interface IOrderServices {
    //处理下单请求，返回下单结果
    DoOrderResponse doOrder(DoOrderRequest doOrderRequest) throws InterruptedException;

}
