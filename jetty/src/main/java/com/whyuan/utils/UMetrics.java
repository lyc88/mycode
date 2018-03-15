package com.whyuan.utils;

import com.codahale.metrics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
*@Author: whyuan
*@Description: 封装服务监控API Metrics，MetricRegistry
*@Date: Created in 10:06 2017/12/29
 *Metrics提供了Gauge、Counter、Meter、Histogram、Timer等度量工具类以及Health Check功能。
 *
 * 统计某个函数被调用的频率（TPS），会使用Meters
 * 统计某个函数的执行耗时时，会使用Histograms
 * 统计TPS又要统计耗时时，会使用Timers
*@Modified By:
*/
public class UMetrics {
    private final static Logger logger = LoggerFactory.getLogger(UMetrics.class);

    private static final ConcurrentHashMap<String,MetricRegistry> metrics = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String,ScheduledReporter> reporters = new ConcurrentHashMap();


    public static MetricRegistry create(Logger clogger, String name, long period, TimeUnit unit){
        MetricRegistry metric = metrics.get(name);

        if (metric == null) {
            metric = new MetricRegistry();
            metrics.put(name, metric);

            //Reporter接口，用于展示metrics 获取到的统计数据
            Slf4jReporter reporter = Slf4jReporter.forRegistry(metric).filter((name1, metric1) -> {
                //计数器
                if (metric1 instanceof Counter) {
                    clogger.info("count={},name={}", ((Counter) metric1).getCount(), name1);
                    return false;
                }

                //瞬间时状态度量，例如衡量待处理队列中的任务数
                if (metric1 instanceof Gauge) {
                    clogger.info("{}={}", name1, ((Gauge) metric1).getValue());
                    return false;
                }

                return true;
            }).outputTo(clogger).build();
            reporter.start(period, unit);

            reporters.putIfAbsent(name, reporter);
        }





        return metric;
    }

    public static void finallyPrint(MetricRegistry metric){
        metric.getMetrics().forEach((name, $metric) -> {
            if ($metric instanceof Counter) {
                logger.info("LAST==>count={},name={}", ((Counter) $metric).getCount(), name);
            }
            if ($metric instanceof Gauge) {
                logger.info("LAST==>{}={}", name, ((Gauge) $metric).getValue());
            }
        });
    }




    public static void closeAll() {
        reporters.forEach((name, reporter) -> reporter.close());

        reporters.clear();
        metrics.clear();
    }
}
