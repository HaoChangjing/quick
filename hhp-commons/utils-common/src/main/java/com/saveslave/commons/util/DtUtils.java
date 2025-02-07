package com.saveslave.commons.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @ClassName: DtUtils
 * @Description: 日期处理
 *
 */
public class DtUtils {

	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 时间格式(yyMMdd)
	 */
	public static final SimpleDateFormat yyMMdd = new SimpleDateFormat("yyMMdd");

	private static final DateFormat FORMATER_DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");

	public static Date toDate(String d) throws Exception {
		return FORMATER_DATE_YMD.parse(d);
	}

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	/**
	 * 计算距离现在多久，非精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBefore(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		} else if (hour > 0) {
			r += hour + "小时";
		} else if (min > 0) {
			r += min + "分";
		} else if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}

	/**
	 * 计算距离现在多久，精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBeforeAccurate(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		}
		if (hour > 0) {
			r += hour + "小时";
		}
		if (min > 0) {
			r += min + "分";
		}
		if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}

	public static String addDay(Date s, int n) {

		SimpleDateFormat FORMATER_DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cd = Calendar.getInstance();
		cd.setTime(s);
		cd.add(Calendar.DATE, n);// 增加一天
		// cd.add(Calendar.MONTH, n);//增加一个月
		return FORMATER_DATE_YMD.format(cd.getTime());

	}

	/**
	 * 返回当前时间的"yyyy-MM-dd"格式字符串
	 */
	public static String currentDay() {
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		return formater.format(new Date());
	}
	
	/**
	 * 返回当前时间的"yyyy-MM-dd"格式字符串
	 */
	public static String currentTime() {
		DateFormat formater = new SimpleDateFormat(DATE_TIME_PATTERN);
		return formater.format(new Date());
	}

	public static int calculateDaysNew(Date first, Date second) {
		int days = 0;

		if (second.before(first)) {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(second);
			calendar1.set(Calendar.HOUR_OF_DAY, 0);
			calendar1.set(Calendar.MINUTE, 0);
			calendar1.set(Calendar.SECOND, 0);
			calendar1.set(Calendar.MILLISECOND, 0);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(first);
			calendar2.set(Calendar.HOUR_OF_DAY, 0);
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND, 0);
			calendar2.set(Calendar.MILLISECOND, 0);
			while (calendar1.compareTo(calendar2) != 0) {
				calendar1.add(Calendar.DAY_OF_YEAR, 1);
				days++;
			}
			days = -days;
		} else {
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(first);
			calendar1.set(Calendar.HOUR_OF_DAY, 0);
			calendar1.set(Calendar.MINUTE, 0);
			calendar1.set(Calendar.SECOND, 0);
			calendar1.set(Calendar.MILLISECOND, 0);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(second);
			calendar2.set(Calendar.HOUR_OF_DAY, 0);
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND, 0);
			calendar2.set(Calendar.MILLISECOND, 0);
			while (calendar1.compareTo(calendar2) != 0) {
				calendar1.add(Calendar.DAY_OF_YEAR, 1);
				days++;
			}
		}

		return days;
	}

	/**
	 * 获取当月的第一天
	 *
	 * @return
	 */
	public static String geFirstDayByMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return FORMATER_DATE_YMD.format(c.getTime());
	}

	/**
	 * 获取当月的第一天
	 *
	 * @return
	 */
	public static Date geFirstDayDateByMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return c.getTime();
	}

	/**
	 * 获取当月的最后一天
	 *
	 * @return
	 */
	public static String geLastDayByMonth() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return FORMATER_DATE_YMD.format(ca.getTime());
	}

	/**
	 * 获取当前周的第一天：
	 * 
	 * @return
	 */
	public static Date getFirstDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_WEEK, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cal.getTime();
	}

	/**
	 * 获取当前周最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfWeek() {
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_WEEK, 1);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 6);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cal.getTime();
	}
	/**
	 * 当前日期
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}
	/**
	 * 日期转换为字符串
	 * @param date_sdf
	 * @return
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date=getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}

	/**
	 * 获取到当天结束还有多少秒
	 * @return
	 */
	public static Long getNowEndMillis(){
		Calendar nowDate = Calendar.getInstance();
		//设置当前时间的23点59分59秒
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23); // Calendar.HOUR 12小时制；HOUR_OF_DAY 24小时制
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		//System.out.println("时间为："+datetimeFormat.format(todayEnd.getTime()));
		return (todayEnd.getTimeInMillis() - nowDate.getTimeInMillis()) / 1000;
	}
	public static void main(String[] args) throws Exception {
		// System.out.println(DtUtils.addDay(new Date(), -7));
		// System.out.println(DtUtils.calculateDaysNew(DtUtils.toDate(DtUtils.addDay(new Date(), -7)), new Date()));
		LocalDateTime prodDateTime = LocalDateTime.ofInstant(DateUtil.beginOfDay(new Date()).toInstant(),
				TimeZone.getDefault().toZoneId());
		LocalDateTime deliveryDateTime = LocalDateTime.ofInstant(parseDate("2025-01-22 14:00:00").toInstant(),
				TimeZone.getDefault().toZoneId());
		System.out.println(DtUtils.ceilDaysBetween(prodDateTime, deliveryDateTime));
	}

	/**
	 * 根据日期的格式自动转换为日期格式<br>
	 *
	 * @param dateStr                   日期字符串
	 * @return                          日期
	 * @throws NullPointerException     入参为空异常
	 * @throws IllegalArgumentException 不支持的入参异常
	 */
	public static Date parseDate(String dateStr) throws NullPointerException, IllegalArgumentException, ParseException {
		Validator.validateNotEmpty(dateStr, "dateStr cannot be empty");
		if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			return DateUtils.parseDate(dateStr, DatePattern.NORM_DATETIME_PATTERN);
		}
		if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return DateUtils.parseDate(dateStr, DatePattern.NORM_DATE_PATTERN);
		}
		if (dateStr.matches("\\d{14}")) {
			return DateUtils.parseDate(dateStr, DatePattern.PURE_DATETIME_PATTERN);
		}
		if (dateStr.matches("\\d{8}")) {
			return DateUtils.parseDate(dateStr, DatePattern.PURE_DATE_PATTERN);
		}
		throw new IllegalArgumentException("不支持的日期格式");
	}

	/**
	 * 计算两个日期时间之间的天数差，并向上取整
	 *
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return 	    向上取整后的天数差
	 */
	public static int ceilDaysBetween(LocalDateTime start, LocalDateTime end) {
		// 计算时间差（以秒为单位）
		Duration duration = Duration.between(start, end);
		// 转换为天数（向上取整）
		double daysDifference = duration.getSeconds() / (double) (24 * 60 * 60);
		return (int) Math.ceil(daysDifference);
	}

}
