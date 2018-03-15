package com.whyuan.configuration;

import java.util.Map;
/**
*@Author: whyuan
*@Description: Jetty配置的对象映射
*@Date: Created in 13:38 2017/12/28
*@Modified By:
*/
public class JettyConf {
    private int port;
    private int minThreads;
    private int maxThreads;

    public JettyConf(){}
    public JettyConf(Map<String,String> jettyConf , String jettyPrefix){
        this.port=Integer.parseInt(jettyConf.getOrDefault(jettyPrefix+"port","8888"));
        this.minThreads = Integer.parseInt(jettyConf.getOrDefault(jettyPrefix + "minThreads", "10"));
        this.maxThreads = Integer.parseInt(jettyConf.getOrDefault(jettyPrefix + "maxThreads", "150"));
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(int minThreads) {
        this.minThreads = minThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public String toString() {
        return "JettyConf{" +
                "port=" + port +
                ", minThreads=" + minThreads +
                ", maxThreads=" + maxThreads +
                '}';
    }
}
