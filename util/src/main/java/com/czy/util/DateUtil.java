package com.czy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具
 * 默认格式为yyyy-MM-dd HH:mm:ss
 * 日期转换为严格，只有是实际存在的日期才能正确转换.默认宽容会把不符合规则的日期格式也转换。sdf.setLenient(false);
 * 时区为中国时区 sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
 *
 * @author chenzy
 * @date 2019.12.24
 */
public class DateUtil {
    private DateUtil() {

    }

    /*
     public final static String yyMMddHHmmSSSSS = "yyMMddHHmmssSSS";

     public final static String yyMMddHHmmSS = "yyMMddHHmmss";
     public final static String yyMMddHHmm = "yyMMddHHmm";
     public final static String yyyyMMdd = "yyyyMMdd";


     public final static String yy = "yy";


     public final static String MMdd = "MMdd";
     public final static String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
     public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
     public final static String yyyy_MM = "yyyy-MM";
     public final static String MM_dd = "MM-dd";
     public final static String C_yyyy_MM_dd_hh_mm_SS = "yyyy年MM月dd日 HH时mm分ss秒";
     public final static String C_yyyy_MM_dd_hh_mm = "yyyy年MM月dd日 HH时mm分";
     public final static String C_yyyy_MM_dd = "yyyy年MM月dd日";
     public final static String C_yyyy_MM = "yyyy年MM月";
     public final static String MM_dd_HH_mm = "MM-dd HH:mm";
     public final static String HHmmss = "HH:mm:ss";
     public final static String HHmm = "HH:mm";
     public final static String mm = "mm";
     public final static String ss = "ss";
     public final static String noHHmmss = "HHmmss";*/
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

    private final static SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT, Locale.CHINA);

    static {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //默认为宽容，会把不符合规则的日期格式也转换。设为false将禁用，只有是实际存在的日期才能正确转换
        sdf.setLenient(false);
    }

    public static String data2Str(Date date, String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static String data2Str(Date date) {
        return data2Str(date, DEFAULT_FORMAT);
    }

    /**
     * 日期转字符串，只取年月日数据
     *
     * @param date
     * @return
     */
    public static String data2StrDay(Date date) {
        return data2Str(date, yyyy_MM_dd);
    }

    public static Date str2Date(String date, String pattern) {
        try {
            sdf.applyPattern(pattern);
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date str2Date(String date) {
        return str2Date(date, DEFAULT_FORMAT);
    }

    /**
     * 判断字符串是否为默认日期格式
     */
    public static boolean isDate(String date) {
        return isDate(date, DEFAULT_FORMAT);
    }

    /**
     * 判断字符串是否为指定日期格式
     */
    public static boolean isDate(String date, String pattern) {
        try {
            sdf.applyPattern(pattern);
            sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 得到完整的年 四位 2020
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        return data2Str(date, yyyy);
    }

    /**
     * 得到完整的年 四位 2020
     *
     * @param date
     * @return
     */
    public static Integer getYearInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到两位的月份 2月得到字符串02
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        return data2Str(date, MM);
    }

    /**
     * 得到月份 1-11，以0开始 2月得到数字1
     *
     * @param date
     * @return
     */
    public static Integer getMonthInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 得到两位的小时
     *
     * @param date
     * @return
     */
    public static String getHour(Date date) {
        return data2Str(date, HH);
    }

    /**
     * 计算两个时间戳相差多少秒
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long calSecond(Long time1, Long time2) {
        long time = Math.abs((time2 - time1) / 1000);
        return time;
    }

    /**
     * 计算两个时间戳相差多少分钟
     * @return
     */
    public static long calMinute(Date date1, Date date2) {
        long time = Math.abs((date2.getTime() - date1.getTime()) / 60000);
        return time;
    }

    /**
     * 判断时间是否在某一时间段
     *
     * @param startHour 开始时间的小时
     * @param endHour   结束时间的小时
     * @param date      要判断的时间
     * @return
     */
    public static boolean isInTime(Integer startHour, Integer endHour, Date date) {
        Integer hour = Integer.valueOf(getHour(date));
        return hour >= startHour && hour < endHour;
    }

    /**
     * 判断时间是否在某一时间段
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param date  当前时间
     * @return
     */
    public static boolean isInTime(Date start, Date end, Date date) {
        if (date.getTime() >= start.getTime() && date.getTime() <= end.getTime()) {
            return true;
        }
        return false;
    }
    /**
     * 日期前后移动指定分钟数.正整数往后推,负数往前移动
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date moveMinute(Date date, Integer minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 日期前后移动指定天数.正整数往后推,负数往前移动
     *
     * @param date
     * @param day
     * @return
     */
    public static Date moveDay(Date date, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 日期前后移动指定天数.正整数往后推,负数往前移动
     *
     * @param date
     * @param day
     * @return
     */
    public static String moveDay(Date date, int day, String pattern) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day);
        return data2Str(calendar.getTime(), pattern);
    }

    /**
     * 得到当天的整点
     * 传1得到 当天的凌晨1点 分秒都是0
     *
     * @param hour
     * @return
     */
    public static Date getDateHourly(Integer hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 得到指定日期的整点
     * 传1得到 当天的凌晨1点 分秒都是0
     *
     * @param hour
     * @return
     */
    public static Date getDateHourly(Date date, Integer hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static void main(String[] args) {
        // Date nextMonthFirstDate = nextMonthFirstDate();
//       System.out.println(DateUtil.StrToDateTime("2000-01-01 00:00:00",DateUtil.DEFAULT_FORMAT));
//        Long machinetime = getDatetimeByMoveMinute(new Date(),1).getTime();
//        System.out.println(calLastedTime(1534330596449L,1534329819053L));
//        System.out.println(getDesignationTimestamp(1));
//        System.out.println(new Date(1534438800879L));

//        System.out.println(DateUtil.isInTime(DateUtil.getDesignationTimestamp(15), DateUtil.getDesignationTimestamp(16), new Date()));
        String date = "208727904710294";
        boolean isDate = isDate(date, "yyyyMMddHHmmss");
        System.out.println(isDate);
        int year = getYearInt(new Date());
        System.out.println(year);
        System.out.println(getYear(new Date()));
    }


}
