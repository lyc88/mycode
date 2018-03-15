package com.whyuan.$6utils.monitor;

import com.codahale.metrics.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

//Metric性能监控
public class MetricUtils {
    private final static Logger logger = LoggerFactory.getLogger(UMetrics.class);

    private static final ConcurrentHashMap<String,MetricRegistry> metrics = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String,ScheduledReporter> reporters = new ConcurrentHashMap();


    public static MetricRegistry create(Logger clogger,String name, long period, TimeUnit unit){
        MetricRegistry metric = metrics.get(name);
        if (metric == null) {
            metric = new MetricRegistry();
            metrics.put(name, metric);

            Slf4jReporter reporter = Slf4jReporter.forRegistry(metric).filter((name1, metric1) -> {
                if (metric1 instanceof Counter) {
                    clogger.info("count={},name={}", ((Counter) metric1).getCount(), name1);
                    return false;
                }

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
