/**
 * Copyright 2015 the original author or Linlan authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.linlan.commons.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * Filename:DateUtils.java
 * Desc:日期时间处理工具类，提供日期格式定义，日期处理方法
 *
 * @author linlan
 * @author <a href="mailto:20400301@qq.com">linlan</a>
 * CreateTime:2017-07-15 6:19 PM
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class DateUtils {

    /** The zero. */
    private static String ZERO = "0";

    /** time format of yyyy-MM-dd */
    public final static String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMdd_simple = "yyyyMMdd";

    /** long time format yyyy-MM-dd HH:mm:ss */
    public final static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmmss_simple = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmss_cn = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String yyyy = "yyyy";
    public static final String MMdd = "MM-dd";
    public static final String MM = "MM";
    public static final String dd = "dd";
    public static final String HHMM = "HH:mm";
    public static final String yyyy_cn = "yyyy年";
    public static final String MMdd_cn = "MM月dd日";
    public static final String yyyyMMdd_cn = "yyyy年MM月dd日";
    public static final String[] WEEKS = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };


    /**
     * Gets the calendar.
     *
     * @return the calendar
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * get the now of string
     * @return the current time of String
     */
    public static String getNowString() {
        Calendar calendar = getCalendar();
        /** The buffer. */
        StringBuffer buffer = new StringBuffer();
        buffer.delete(0, buffer.capacity());
        buffer.append(calendar.get(Calendar.YEAR));

        if (calendar.get(Calendar.MONTH) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(calendar.get(Calendar.MONTH));

        if (calendar.get(Calendar.DATE) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(calendar.get(Calendar.DATE));
        if (calendar.get(Calendar.HOUR) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(calendar.get(Calendar.HOUR));
        if (calendar.get(Calendar.MINUTE) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(calendar.get(Calendar.MINUTE));
        if (calendar.get(Calendar.SECOND) < 10) {
            buffer.append(ZERO);
        }
        buffer.append(calendar.get(Calendar.SECOND));
        return buffer.toString();
    }


    /**
     * format date of default pattern yyyy-MM-dd
     * @param date the input date
     * @return the string of input date
     */
    public static String format(Date date) {
        return format(date, yyyyMMdd);
    }

    /**
     * format date with input pattern
     * @param date the input date
     * @param pattern the input pattern
     * @return the string of input date
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 获取系统时间
     * @param date 时间日期对象
     * @return the string of input date
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }

    /**
     * 得到指定时间的时间日期格式
     *
     * @param source 指定的时间
     * @param format 时间日期格式
     * @return date object
     */
    public static Date formatDate(String source, String format) {
        if (StringUtils.isBlank(format)) {
            format = yyyyMMddHHmmss;
        }
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到指定时间的时间日期格式
     * @param date 指定的时间
     * @param format 时间日期格式
     * @return the string of input date
     */
    public static String getFormatDateTime(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static void main(String[] args) {
        String date1 = "2014年7月";
        String date2 = "2014年07月";
        String date3 = "2014.08";
        String date4 = "2014.8";

        Date d1 = parseDate(date1);
        Date d2 = parseDate(date2);
        Date d3 = parseDate(date3);
        Date d4 = parseDate(date4);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
        System.out.println(DateUtils.getNowString());

    }
    /**
     * 通过字符串渲染日期
     * @param strDate 指定的时间字符串
     * @return date object
     */
    public static Date parseDate(String strDate) {
        if (strDate == null || "".equals(strDate)) {
            return null;
        }
        Date date = null;
        String[] formats = { "yyyy.MM", "yyyy.M", "yyyy年MM月", "yyyy年M月", "yyyy-MM", "yyyy-M", "yyyy年MM", "yyyy年M" };

        date = marchDatePattern(formats, strDate, 0);

        return date;
    }


    /**
     * 将Object显示转换成Date
     * @param source 输入对象
     * @return data object
     */
    public static Date formatObject(Object source){
        Date fmtDate = null;
        if(source != null){
            try{
                fmtDate = (Date) source;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return fmtDate;
    }

    /**
     * 将long类型数字转换成Date
     * @param num 输入数字
     * @return the string of input date
     */
    public static String formatStr(Long num) {
        SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
        Long time = new Long(num);
        return format.format(time);
    }

    /** 对输入的日期进行队列内字符串格式匹配
     * @param formats 格式数组
     * @param strDate 日期字符串
     * @param i 开始位置
     * @return 日期对象
     */
    private static Date marchDatePattern(String[] formats, String strDate, int i) {
        if (formats.length == i) {
            return null;
        }

        Date date = null;
        boolean isSucc = true;
        try {
            date = new SimpleDateFormat(formats[i]).parse(strDate);
            if (date == null) {
                isSucc = false;
            } else {
                System.out.println("匹配成功: 字串[" + strDate + "], 格式[" + formats[i] + "]");
            }
        } catch (ParseException e) {
            isSucc = false;
            System.out.println("匹配异常: 字串[" + strDate + "], 格式[" + formats[i] + "]. Exception: " + e.getMessage());
        }

        if (!isSucc) {
            date = marchDatePattern(formats, strDate, i + 1);
        }

        return date;
    }

    /**
     * 判断是否是闰年
     * @param date 指定的时间
     * @return true:是闰年,false:不是闰年
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isLeapYear(cal.get(Calendar.YEAR));
    }

    /**
     * 判断是否是闰年
     * @param year 指定的年数字
     * @return true:是闰年,false:不是闰年
     */
    public static boolean isLeapYear(int year) {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.isLeapYear(year);
    }

    /**
     * 判断指定的时间是否是今天
     *
     * @param date 指定的时间
     * @return true:是今天,false:非今天
     */
    public static boolean isInToday(Date date) {
        boolean flag = false;
        Date now = new Date();
        String yyyyMMddHHmmss = getFormatDateTime(now, DateUtils.yyyyMMdd);
        String beginString = yyyyMMddHHmmss + " 00:00:00";
        String endString = yyyyMMddHHmmss + " 23:59:59";
        DateFormat df = new SimpleDateFormat(DateUtils.yyyyMMddHHmmss);
        try {
            Date beginTime = df.parse(beginString);
            Date endTime = df.parse(endString);
            flag = date.before(endTime) && date.after(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断两时间是否是同一天
     *
     * @param from 第一个时间点
     * @param to 第二个时间点
     * @return true:是同一天,false:非同一天
     */
    public static boolean isSameDay(Date from, Date to) {
        boolean isSameDay = false;
        DateFormat df = new SimpleDateFormat(DateUtils.yyyyMMdd);
        String firstDate = df.format(from);
        String secondDate = df.format(to);
        isSameDay = firstDate.equals(secondDate);
        return isSameDay;
    }

    /**
     * 求出指定的时间那天是星期几
     *
     * @param date 指定的时间
     * @return 星期X
     */
    public static String getWeekString(Date date) {
        return DateUtils.WEEKS[getWeek(date) - 1];
    }

    /**
     * 求出指定时间那天是星期几
     *
     * @param date 指定的时间
     * @return 1-7
     */
    public static int getWeek(Date date) {
        int week = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        week = cal.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * 取得指定时间离现在是多少时间以前，如：3秒前,2小时前等 注意：此计算方法不是精确的
     *
     * @param date 已有的指定时间
     * @return 时间段描述
     */
    public static String getAgoTimeString(Date date) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date agoTime = cal.getTime();
        long mtime = now.getTime() - agoTime.getTime();
        String str = "";
        long stime = mtime / 1000;
        long minute = 60;
        long hour = 60 * 60;
        long day = 24 * 60 * 60;
        long weeks = 7 * 24 * 60 * 60;
        long months = 100 * 24 * 60 * 60;
        if (stime < minute) {
            long time_value = stime;
            if (time_value <= 0) {
                time_value = 1;
            }
            str = time_value + "秒前";
        } else if (stime >= minute && stime < hour) {
            long time_value = stime / minute;
            if (time_value <= 0) {
                time_value = 1;
            }
            str = time_value + "分前";
        } else if (stime >= hour && stime < day) {
            long time_value = stime / hour;
            if (time_value <= 0) {
                time_value = 1;
            }
            str = time_value + "小时前";
        } else if (stime >= day && stime < weeks) {
            long time_value = stime / day;
            if (time_value <= 0) {
                time_value = 1;
            }
            str = time_value + "天前";
        } else if (stime >= weeks && stime < months) {
            DateFormat df = new SimpleDateFormat(DateUtils.MMdd);
            str = df.format(date);
        } else {
            DateFormat df = new SimpleDateFormat(DateUtils.yyyyMMdd);
            str = df.format(date);
        }
        return str;
    }

    /**
     * 判断指定时间是否是周末
     *
     * @param date 指定的时间
     * @return true:是周末,false:非周末
     */
    public static boolean isWeeks(Date date) {
        boolean isWeek = false;
        isWeek = (getWeek(date) - 1 == 0 || getWeek(date) - 1 == 6);
        return isWeek;
    }

    /**
     * 得到今天的最开始时间
     *
     * @return 今天的最开始时间
     */
    public static Date getTodayBeginTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        return cal.getTime();
    }

    /**
     * 得到今天的最后结束时间
     *
     * @return 今天的最后时间
     */
    public static Date getTodayEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        return cal.getTime();
    }

    /**
     * 取得本周的开始时间
     *
     * @return 本周的开始时间
     */
    public static Date getThisWeekBeginTime() {
        Date beginTime = null;
        Calendar cal = Calendar.getInstance();
        int week = getWeek(cal.getTime());
        week = week - 1;
        int days = 0;
        if (week == 0) {
            days = 6;
        } else {
            days = week - 1;
        }
        cal.add(Calendar.DAY_OF_MONTH, -days);
        beginTime = cal.getTime();
        return beginTime;
    }

    /**
     * 取得本周的开始日期
     *
     * @param format 时间的格式
     * @return 指定格式的本周最开始时间
     */
    public static String getThisWeekBeginTimeString(String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(getThisWeekBeginTime());
    }

    /**
     * 得到某天最开始时间
     * @param from 时间
     * @return 最开始时间
     */
    public static Date getDateBeginTime(Date from) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

        return cal.getTime();
    }

    /**
     * 得到某天最后的时间
     * @param from 时间
     * @return 最后的时间
     */
    public static Date getDateEndTime(Date from) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);

        return cal.getTime();
    }

    /**
     * 取得本周的结束时间
     *
     * @return 本周的结束时间
     */
    public static Date getThisWeekEndTime() {
        Date endTime = null;
        Calendar cal = Calendar.getInstance();
        int week = getWeek(cal.getTime());
        week = week - 1;
        int days = 0;
        if (week != 0) {
            days = 7 - week;
        }
        cal.add(Calendar.DAY_OF_MONTH, days);
        endTime = cal.getTime();
        return endTime;
    }

    /**
     * 取得本周的结束日期
     *
     * @param format 时间的格式
     * @return 指定格式的本周结束时间
     */
    public static String getThisWeekEndTimeString(String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(getThisWeekEndTime());
    }

    /**
     * 取得两时间相差的天数
     *
     * @param from 第一个时间
     * @param to 第二个时间
     * @return 相差的天数
     */
    public static long getBetweenDays(Date from, Date to) {
        long days = 0;
        long dayTime = 24 * 60 * 60 * 1000;
        long fromTime = from.getTime();
        long toTime = to.getTime();
        long times = Math.abs(fromTime - toTime);
        days = times / dayTime;
        return days;
    }

    /**
     * 取得两时间相差的小时数
     *
     * @param from 第一个时间
     * @param to 第二个时间
     * @return 相差的小时数
     */
    public static long getBetweenHours(Date from, Date to) {
        long hours = 0;
        long hourTime = 60 * 60 * 1000;
        long fromTime = from.getTime();
        long toTime = to.getTime();
        long times = Math.abs(fromTime - toTime);
        hours = times / hourTime;
        return hours;
    }

    /**
     * 取得两时间相差的分钟数
     *
     * @param from 开始时间
     * @param to 结束时间
     * @return 相差分钟数
     */
    public static long getBetweenMinute(Date from, Date to){
        long minute = 0;
        long minuteTime = 60 * 1000;
        long fromTime = from.getTime();
        long toTime = to.getTime();
        long times = Math.abs(fromTime - toTime);
        minute = times / minuteTime;
        return minute;
    }


    /**
     * 获取日期相差月份
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @return 相差月份
     */
    public static int getDiffMonth(Date beginDate,Date endDate) {
        Calendar calbegin = Calendar.getInstance();
        Calendar calend = Calendar.getInstance();
        calbegin.setTime(beginDate);
        calend.setTime(endDate);
        int m_begin = calbegin.get(Calendar.MONTH)+1; //获得合同开始日期月份
        int m_end = calend.get(Calendar.MONTH)+1;
        //获得合同结束日期月份
        int checkmonth = m_end-m_begin+(calend.get(Calendar.YEAR)-calbegin.get(Calendar.YEAR))*12;
        //获得合同结束日期于开始的相差月份
        return checkmonth;
    }

    /**
     * 取得在指定时间上加减days天后的时间
     *
     * @param date 指定的时间
     * @param hours 小时数,正为加，负为减
     * @return 在指定时间上加减  hours 小时 后的时间
     */
    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 取得在指定时间上加减days天后的时间
     *
     * @param date 指定的时间
     * @param days 天数,正为加，负为减
     * @return 在指定时间上加减days天后的时间
     */
    public static Date addDays(Date date, int days) {
        Date time = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        time = cal.getTime();
        return time;
    }

    /**
     * 取得在指定时间上加减months月后的时间
     *
     * @param date 指定时间
     * @param months 月数，正为加，负为减
     * @return 在指定时间上加减months月后的时间
     */
    public static Date addMonths(Date date, int months) {
        Date time = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        time = cal.getTime();
        return time;
    }

    /**
     * 取得在指定时间上加减years年后的时间
     *
     * @param date 指定时间
     * @param years 年数，正为加，负为减
     * @return 在指定时间上加减years年后的时间
     */
    public static Date addYears(Date date, int years) {
        Date time = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        time = cal.getTime();
        return time;
    }

    public static int getWEEK_OF_YEAR() {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 获取calendar实例;
        Calendar calendar = Calendar.getInstance();
        // 判断calendar是不是GregorianCalendar类的实例;
        if (calendar instanceof GregorianCalendar) {
            // System.out.println("属于GregorianCalendar类的实例!");
        }
        // 设置当前时间为:2011-07-24 11:06:00
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR); // 获取年;
        int month = calendar.get(Calendar.MONTH); // 获取月;
        int date = calendar.get(Calendar.DATE); // 获取天;
        int hour = calendar.get(Calendar.HOUR); // 获取小时;
        int minute = calendar.get(Calendar.MINUTE); // 获取分钟;
        int second = calendar.get(Calendar.SECOND); // 获取秒钟;
        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY); // 第几个小时，
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH); // 这天，在一个月内是第几天.
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK); // 这天，在一周内，是第几天.
        int day_of_year = calendar.get(Calendar.DAY_OF_YEAR); // 这天，在一年内，是第几天。
        int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR); // 这周，在一年内是第几周;
        int week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);// 这周，在这个月是第几周;以以星为标准；
        int zone_offset = calendar.get(Calendar.ZONE_OFFSET); // 获取时区;
        int day_of_week_in_month = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH); // 某月中第几周，按这个月1号算，1号起就是第1周，8号起就是第2周。以月份天数为标准
        int r = calendar.get(Calendar.AM_PM);
        if (r == calendar.AM) {
            // System.out.println("现在是上午");
        }

        if (r == calendar.PM) {
            // System.out.println("现在是下午");
        }
        return week_of_year;
    }

    /**
     * 根据身份证获取Date类型的出生日期.
     * @param idcard 18位身份证号码
     * @return 成功Date, 转换过程出现异常null
     */
    public static Date getDateByIdcard(String idcard) {
        String strDate = idcard.substring(6, 14);
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过身份证获取年龄. 精确到月
     * @param idcard 18位身份证号码
     * @return 精确到月的年龄
     */
    public static int getAgeByIdcard(String idcard) {
        int age = 0;
        int year = 0;
        int month = 0;
        if (idcard.length() == 18) {
            year = Integer.parseInt(idcard.substring(6, 10));
            month = Integer.parseInt(idcard.substring(10, 12));
        } else if (idcard.length() == 15) {
            year = Integer.parseInt(idcard.substring(6, 8)) + 1900;
            month = Integer.parseInt(idcard.substring(8, 10));
        } else {
            throw new RuntimeException("身份证[" + idcard + "]格式不正确");
        }

        int currYear = Integer.parseInt(DateUtils.getFormatDateTime(new Date(), "yyyy"));
        int currMonth = Integer.parseInt(DateUtils.getFormatDateTime(new Date(), "MM"));
        age = currYear - year;
        age = month > currMonth ? age - 1 : age;

        return age;
    }

    /**
     * 通过身份证获取出生年
     * @param idcard 18位身份证号码
     * @return 年
     */
    public static String getYearByIdcard(String idcard) {
        if (idcard.length() == 18) {
            return idcard.substring(6, 10);
        } else {
            return "19" + idcard.substring(6, 8);
        }
    }

    /**
     * 通过身份证获取出生月
     * @param idcard 18位身份证号码
     * @return 出生月
     */
    public static String getMonthByIdcard(String idcard) {
        if (idcard.length() == 18) {
            return idcard.substring(10, 12);
        } else {
            return idcard.substring(8, 10);
        }
    }

    /**
     * 通过身份证获取出生日
     * @param idcard 18位身份证号码
     * @return 出生日
     */
    public static String getDayByIdcard(String idcard) {
        if (idcard.length() == 18) {
            return idcard.substring(12, 14);
        } else {
            return idcard.substring(10, 12);
        }
    }

    /**
     * 获取当前年. 格式: YYYY
     *
     * @return 当前年
     */
    public static int getCurrentYear() {
        return Integer.parseInt(DateUtils.getFormatDateTime(new Date(), DateUtils.yyyy));
    }

    /**
     * 获取当前月
     * @return 当前月
     */
    public static int getCurrentMonth() {
        return Integer.parseInt(DateUtils.getFormatDateTime(new Date(), DateUtils.MM));
    }

    /**
     * 得到某一天延后天数的新日期
     * @param date 时间
     * @param days 当前时间前后的天数
     * @return data object
     */
    public static Date getDay(Date date, Object days) {
        int daysInt = Integer.parseInt(days.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysInt);
        return calendar.getTime();
    }

    /**
     * 得到某一天的日期，在当前时间上加、减天数
     * @param strDate 时间字符串
     * @param days 当前时间前后的天数
     * @param format 格式
     * @return data string
     */
    public static String getDay(String strDate, Object days, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return getDay(sdf.parse(strDate), days, format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到某一天的日期，在当前时间上加、减天数
     * @param date 时间
     * @param days 当前时间前后的天数
     * @param format 格式
     * @return data string
     */
    public static String getDay(Date date, Object days, String format) {
        Date tdate = getDay(date, days);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(tdate);
    }

    /**
     * 在当前时间上加、减天数后的新日期
     * @param days 当前时间前后的天数
     * @param format 格式
     * @return data string
     */
    public static String getDay(Object days, String format) {
        return getDay(new Date(), days, format);
    }


    /**
     * 获取某个月内的全部天数
     * @param date 日期格式
     * @return 当月天数
     */
    public static int[] getMonthDays(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int[] days = new int[maxDay];
        for (int i = 1; i <= maxDay; i++) {
            days[i - 1] = i;
        }
        return days;
    }

}
