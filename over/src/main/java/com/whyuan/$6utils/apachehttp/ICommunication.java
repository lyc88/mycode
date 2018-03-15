package com.whyuan.$6utils.apachehttp;

import java.util.Map;

public interface ICommunication{
    String CURRENT_CONN_COUNT = "curConnCount"; //public static final
    String MAX_CONN_COUNT = "maxConnCount";
    String USED_CONN_COUNT = "usedConnCount";

    String communication(Object paramObject) throws Exception;
    boolean init();
    Map<String, Object> getState();
    String watchSelf();
}