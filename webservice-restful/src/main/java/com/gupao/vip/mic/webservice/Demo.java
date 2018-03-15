package com.gupao.vip.mic.webservice;

/**
 */
public class Demo {

    public static void main(String[] args) {
        SayHelloImplService service=new SayHelloImplService();
        SayHelloImpl sayHello=service.getSayHelloImplPort();
        System.out.println(sayHello.sayHello("Mic"));
    }
}
