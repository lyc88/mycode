package com.whyuan.$6utils.thread;

import java.util.Iterator;

public class ThreadUtils {
    /**
     * 根据线程名获取当前线程是否存在。
     *
     * @param name 线程名
     * @return 线程存在返回这个线程，不存在返回null。
     */
    public static synchronized Thread getThreadByName(String name) {
        Iterator<Thread> iterator = Thread.getAllStackTraces().keySet().iterator();
        while (iterator.hasNext()) {
            Thread thread = iterator.next();
            if (thread.getName().equals(name)) {
                return thread;
            }
        }
        return null;
    }

}
