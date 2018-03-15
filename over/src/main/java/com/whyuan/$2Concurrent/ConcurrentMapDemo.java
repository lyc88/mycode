package com.whyuan.$2Concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
//ConcurrentMap为接口，线程安全
//读：不锁Map
//写：只锁操作的那个桶，不锁整个Map
//遍历时：即使被修改，也不会抛ConcurrentModificationException
public class ConcurrentMapDemo {
    public static void main(String[] args) {
        ConcurrentMap concurrentMap=new ConcurrentHashMap();
        concurrentMap.put("key","value");
        Object o=concurrentMap.get("key");
        System.out.println(o);
    }
}
