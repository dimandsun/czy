package com.czy.util.time;

import com.czy.util.text.StringUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author chenzy
 * @since 2020/6/20
 *  java8开始的新时间api
 */
public class TimeUtil {
    private TimeUtil(){}
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy = "yyyy";
    public final static String yyMM = "yyMM";
    public final static String MM = "MM";
    public final static String dd = "dd";
    public final static String yyyyMM = "yyyyMM";
    public final static String HH = "HH";
    public final static String yyMMdd = "yyMMdd";
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    //时区，北京时间
    private final static ZoneOffset zone=ZoneOffset.of("+8");
    public static LocalDateTime now(){
        return LocalDateTime.now(zone);
    }
    /**
     * 得到当前日期时间戳
     * @return
     */
    public static Long nowLong(){
        return now().toInstant(zone).toEpochMilli();
    }
    public static String nowStr(){
        return nowStr(DEFAULT_FORMAT);
    }
    /**
     * 得到当前日期字符串，指定格式
     * @param pattern
     * @return
     */
    public static String nowStr(String pattern){
        if (StringUtil.isBlank(pattern)){
            pattern=DEFAULT_FORMAT;
        }
        return DateTimeFormatter.ofPattern(pattern).format(now());
    }
    public static boolean isInTime(LocalDateTime beginTime, LocalDateTime endTime) {
        LocalDateTime time = now();
        return time.isAfter(beginTime)&&time.isBefore(endTime);
    }
    public static boolean isInTime(LocalDateTime beginTime, LocalDateTime endTime, LocalDateTime time) {
        return time.isAfter(beginTime)&&time.isBefore(endTime);
    }


}
