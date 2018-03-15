package com.whyuan.$6utils.datetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 *@Author: whyuan
 *@Description: 6-12位数字格式时间操作
 *@Date: Created in 12:07 2017/12/26
 *@Modified By:
 */
public class TimeHelper {

    private static final Logger logger = LoggerFactory.getLogger(TimeHelper.class);

    private static boolean canParse(String time) {
        return time.matches("\\d{6}") || time.matches("\\d{8}") || time.matches("\\d{10}") || time.matches("\\d{12}");
    }

    public static long startTime(String start) throws ParseException {
        long stime = 0;
        if (start.matches("\\d{6}"))
            stime = YYYY_MM().parse(start).getTime();
        if (start.matches("\\d{8}"))
            stime = YYYY_MM_DD().parse(start).getTime();
        if (start.matches("\\d{10}"))
            stime = YYYY_MM_DD_HH().parse(start).getTime();
        if (start.matches("\\d{12}"))
            stime = YYYY_MM_DD_HH_MM().parse(start).getTime();
        return stime;
    }

    public static long endTime(String end) throws ParseException {
        long etime = 0;
        if (end.matches("\\d{6}"))
            etime = YYYY_MM().parse(end).getTime();
        if (end.matches("\\d{8}"))
            etime = YYYY_MM_DD().parse(end).getTime() + TimeUnit.DAYS.toMillis(1) - 1;
        if (end.matches("\\d{10}"))
            etime = YYYY_MM_DD_HH().parse(end).getTime() + TimeUnit.HOURS.toMillis(1) - 1;
        if (end.matches("\\d{12}"))
            etime = YYYY_MM_DD_HH_MM().parse(end).getTime() + TimeUnit.MINUTES.toMillis(1) - 1;
        return etime;
    }

    private static SimpleDateFormat YYYY_MM() {
        return new SimpleDateFormat("yyyyMM");
    }

    private static SimpleDateFormat YYYY_MM_DD() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    private static SimpleDateFormat YYYY_MM_DD_HH() {
        return new SimpleDateFormat("yyyyMMddHH");
    }

    private static SimpleDateFormat YYYY_MM_DD_HH_MM() {
        return new SimpleDateFormat("yyyyMMddHHmm");
    }
}
