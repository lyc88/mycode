package com.whyuan.$6utils.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//时间工具类
public class DateTimes {
    //private final static Logger logger = LoggerFactory.getLogger(DateTimes.class);

    private static final String FAILURE = "0001-01-01T00:00:00";


    //默认时间：yyyy-MM-dd'T'HH:mm:ssXXX
    public static String format(Date date) {
        return format(date, STYLE.DEFAULT);
    }

    //传入规则解析
    public static String format(Date date, STYLE style) {
        SimpleDateFormat format = new SimpleDateFormat(style.toS());
        return format.format(date);
    }

    /**
     * @param df
     * @param source
     * @return
     * @throws ParseException
     */
    public static final String getDate(DateFormat df, String source) {
        DateFormat _df_ = new SimpleDateFormat("yyyyMMdd");
        return _df_.format(format(df, source));
    }

    public static final String getDate(long source) {
        DateFormat _df_ = new SimpleDateFormat("yyyyMMdd");
        return _df_.format(source);
    }

    public static final DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String formatNOW() {
        SimpleDateFormat format = new SimpleDateFormat(STYLE.DEFAULT.toS());
        return format.format(new Date());

    }

    public static long toTime(String str, STYLE style) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(style.toS());
        return format.parse(str).getTime();
    }

    public enum STYLE {
        /** */
        DEFAULT("yyyy-MM-dd'T'HH:mm:ssXXX"),
        YYYYMMDD("yyyyMMdd"),
        YYYYMM("yyyyMM"),
        HHMMSS("HH:mm:ss"),
        HHMMSSXXX("HH:mm:ss");
        private String style = "yyyy-MM-dd'T'HH:mm:ssXXX";

        STYLE(String style) {
            this.style = style;
        }

        public String toS() {
            return this.style;
        }

    }

    public static final Date format(DateFormat df, String source) {
        try {
            return df.parse(source);
        } catch (Exception e) {
            if (source.equals(FAILURE)) {
                return now();
            }
        }

        try {
            DateFormat __df__ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return __df__.parse(source);
        } catch (ParseException e) {
        }

        try {
            DateFormat _default = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            return _default.parse(source);
        } catch (ParseException e) {
            //logger.error("", e);
        }
        return now();
    }

    public static final Date now() {
        return new Date(System.currentTimeMillis());
    }

}
