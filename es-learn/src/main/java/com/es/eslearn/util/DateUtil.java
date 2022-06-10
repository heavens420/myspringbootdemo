package com.es.eslearn.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:时间工具类
 * @ClassName DateUtil
 * @Author whj
 * @date 2020.12.12 10:52
 */
public class DateUtil {

    /**
     * 获取当前时间，格式为年-月-日 时:分:秒
     *
     * @return
     */
    public static String getCurrTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = format.format(new Date());
        return currDate;
    }

    /**
     * 获取当前时间，格式为年月日时分秒
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String currDate = format.format(new Date());
        return currDate;
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static String getYear() {
        String strYear = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        strYear = formatter.format(currentDate);
        return strYear;
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static String getYearMonth() {
        String strYearMonth = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        strYearMonth = formatter.format(currentDate);
        return strYearMonth;
    }

    /**
     * 获取指定日期格式的日期
     *
     * @param format
     * @return
     */
    public static String getDateByFormat(String format) {
        String date = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        date = formatter.format(currentDate);
        return date;
    }

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getDate() {
        String strCurrentDate = "";
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        strCurrentDate = formatter.format(currentDate);
        return strCurrentDate;
    }


    /**
     * 获取指定格式的日期
     *
     * @param dateFormat  日期格式
     * @param intervalDay 与当天相隔的天数(0:当天 1:明天 -1：昨天)
     * @return
     */
    public static String getSpecialDate(String dateFormat, int intervalDay) {
        String strCurrentDate = "";
        // Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, intervalDay);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        strCurrentDate = formatter.format(c.getTime());

        return strCurrentDate;
    }

    /**
     * 功能：获取间隔一定分钟数的日期
     * 修改时间：Jul 27, 2011 10:46:53 AM
     *
     * @param dateFormat
     * @param intervalMinute
     * @return
     */
    public static String getSpecialDateByIntervalMinute(String dateFormat, int intervalMinute) {
        Date date = new Date();
        long time = date.getTime() + intervalMinute * 60 * 1000;
        Date preDate = new Date(time);

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        return formatter.format(preDate);
    }

    /**
     * 获取当前时间时分秒
     *
     * @return
     */
    public static String getTime() {
        String strCurrentTime = "";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        strCurrentTime = formatter.format(currentTime);
        return strCurrentTime;
    }

    /**
     * 获取当前小时
     *
     * @return
     */
    public static String getCurrentHour() {
        String strCurrentTime = "";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        strCurrentTime = formatter.format(currentTime);
        return strCurrentTime;
    }

    /**
     * 获取当前分钟
     *
     * @return
     */
    public static String getCurrentMinute() {
        String strCurrentTime = "";
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("mm");
        strCurrentTime = formatter.format(currentTime);
        return strCurrentTime;
    }

    /**
     * 根据日期格式获取某个时间值
     *
     * @param specialDate
     * @param dateFormat
     * @return
     */
    public static String gettDateStrFromSpecialDate(Date specialDate, String dateFormat) {
        String strCurrentTime = "";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        strCurrentTime = formatter.format(specialDate);
        return strCurrentTime;
    }

    /**
     * 数字时间格式化
     * 234359->23:43:59
     *
     * @param time
     * @return
     */
    public static String numberToStringTime(String time) {
        String strTime = "";
        if (time.length() == 6) {
            String hh = time.substring(0, 2);
            String mm = time.substring(2, 4);
            String ss = time.substring(4, 6);
            strTime = (new StringBuilder(String.valueOf(hh))).append(":")
                    .append(mm).append(":").append(ss).toString();
        }
        return strTime;
    }

    /**
     * 数字日期格式化
     * 20090806->2009-08-06
     *
     * @param date
     * @return
     */
    public static String numberToStringDate(String date) {
        String strTime = "";
        // System.out.println("date.length:"+date.length());
        if (date.length() == 8) {
            String year = date.substring(0, 4);
            String month = date.substring(4, 6);
            String day = date.substring(6, 8);
            strTime = (new StringBuilder(String.valueOf(year))).append("-")
                    .append(month).append("-").append(day).toString();
        }
        return strTime;
    }

    /**
     * 返回两个string类型日期之间相差的天数
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;

        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * String转Date
     *
     * @param s
     * @return
     */
    public static Date transferDate(String s) {
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdfTime.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断日期是一周中的第几天
     *
     * @param str
     * @return
     */
    public static int getWeekWhich(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transferDate(str));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定天数前后的日期
     *
     * @param day 指定天数
     * @param s   加减
     * @return
     */
    public static String getNamedDays(int day, String s) {
        Calendar canlendar = Calendar.getInstance();
        if (s.equals("+")) {
            canlendar.add(Calendar.DATE, day); // 日期减 如果不够减会将月变动
        } else {
            canlendar.add(Calendar.DATE, -day); // 日期减 如果不够减会将月变动
        }
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * @param @param  day
     * @param @param  s
     * @param @return
     * @return String
     * @throws
     * @Title: getNamedDaysNew
     * @Description: 获取年月日格式的日期
     */
    public static String getNamedDaysNew(int day, String s) {
        Calendar canlendar = Calendar.getInstance();
        if (s.equals("+")) {
            canlendar.add(Calendar.DATE, day); // 日期减 如果不够减会将月变动
        } else {
            canlendar.add(Calendar.DATE, -day); // 日期减 如果不够减会将月变动
        }
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 比较两个日期大小
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取今日开始结束时间
     *
     * @return
     */
    public static Map<String, String> getDaysBeginAndEndTime() {
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00");
        String beginTime = format1.format(new Date());
        String endTime = format.format(new Date());
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 获取昨天的开始结束时间
     *
     * @return
     */
    public static Map<String, String> getYesterdayBeginAndEndTime() {
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00");
        Date today = new Date();
        Date yestoday = new Date(today.getTime() - 24 * 3600 * 1000);
        String endTime = format.format(yestoday);
        String beginTime = format1.format(yestoday);
        map.put("beginTime", beginTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 获取昨天的时间
     * @return
     */
    public static String getYesterdayTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date yestoday = new Date(today.getTime() - 24 * 3600 * 1000);
        return format.format(yestoday);
    }
    /**
     * 日期转换
     * @return
     */
    public static String formatTimeHour(String dayTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH");
            Date date=sdfTime.parse(dayTime);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 日期转换
     * @return
     */
    public static String formatTimeDay(String dayTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd");
            Date date=sdfTime.parse(dayTime);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本周的第一天
     *
     * @return String
     **/
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00";
    }

    /**
     * 获取本周的最后一天
     *
     * @return String
     **/
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59";
    }

    /**
     * 获取本月开始日期
     *
     * @return String
     **/
    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00";
    }

    /**
     * 获取本月最后一天
     *
     * @return String
     **/
    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59";
    }

    /**
     * 获得近一周的开始时间和结束时间
     * @return
     */
    public static Map<String,String> getDaySevenRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,String> condition=new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        condition.put("endTime",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-144);
        condition.put("beginTime",df.format(calendar.getTime()));
        return condition;
    }

    /**
     * 获得近一月的开始时间和结束时间
     * @return
     */
    public static Map<String,String> getDayTRange(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,String> condition=new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        condition.put("endTime",df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY,-720);
        condition.put("beginTime",df.format(calendar.getTime()));
        return condition;
    }
    public static String addHour(String dayTime) {
        Date date=transferDate(dayTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.HOUR,8);//12小时制
        calendar.add(Calendar.HOUR_OF_DAY,1);//24小时制
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        return df.format(date);
    }

}
