package com.czy.util.time;

import java.text.ParseException;
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
    public static String data2Str(Date date, String pattern) {
//        sdf.applyPattern(pattern);
        return null;//sdf.format(date);
    }
    public static String now(){
        return data2Str(new Date());
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
//            sdf.applyPattern(pattern);
            return null;// sdf.parse(date);
        } catch (Exception e) {
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
//            sdf.applyPattern(pattern);
//            sdf.parse(date);
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

}
