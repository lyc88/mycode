package com.whyuan.$6utils.apachehttp;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception

    {
        Map map = new HashMap();
        map.put("Content-Type","application/json;charset=utf-8");
        map.put("accept","application/json,text/javascript,*/*");
        map.put("X_REQUESTED_WITH","XMLHttpRequest");
        HttpCommunication httpCommunication = new HttpCommunication
                (3000, 3000, 3000,
                        "http://localhost:8888", "post", 100,
                        "keepAlive", "UTF-8", "none", map);
       httpCommunication.communication("我爱你我的家");
    }
}
