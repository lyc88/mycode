package com.whyuan.user;

import com.alibaba.dubbo.rpc.RpcContext;
import com.whyuan.order.DoOrderRequest;
import com.whyuan.order.DoOrderResponse;
import com.whyuan.order.IOrderServices;
import com.whyuan.order.IQueryServices;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("order-consumer.xml");
        //用户下单过程
        IOrderServices iOrderServices=(IOrderServices)context.getBean("orderServices");

        DoOrderRequest doOrderRequest=new DoOrderRequest();
        doOrderRequest.setName("whyuan");
        //未指定async=true，表示同步操作，该方法阻塞
        //DoOrderResponse doOrderResponse=iOrderServices.doOrder(doOrderRequest);

        //指定async=true,异步操作。
        iOrderServices.doOrder(doOrderRequest);//该方法不再阻塞
        Future<DoOrderResponse> future=RpcContext.getContext().getFuture();
        System.out.println("此处模拟异步操作后面的代码，如果结果在我后面输出表示异步回调成功");
        DoOrderResponse doOrderResponse=future.get();//阻塞
        System.out.println("服务一的处理结果："+doOrderResponse);


        //接收异步操作的结果返回


        //查询订单过程
        IQueryServices iQueryServices=(IQueryServices)context.getBean("queryServices");
        System.out.println("服务二的处理结果："+iQueryServices.doQuery("我说啥，你也说啥？"));


        System.in.read();

    }
}
