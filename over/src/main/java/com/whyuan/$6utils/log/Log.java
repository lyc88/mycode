package com.whyuan.$6utils.log;

import java.util.Map;

public interface Log<C, T> {

    String THREAD_NAME = "loggerSink-thread";

    void put(Map<String, Object> src);

    void index(C c, T t);

}
