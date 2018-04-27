package hg.fx.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 时间日期解析格式化工具类
 * </p>
 * <p>
 * 由于SimpleDateFormat对象是线程不安全对象，每次调用解析和格式化方法都创建一个新的格式化对象
 * </p>
 * 
 * @author zhurz
 */
public class DateUtil {

	// 直接使用静态变量会导致线程不安全
	/** 新创建<strong>yyyy-MM-dd HH:mm:ss</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_TIME_FORMAT1() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/** 新创建<strong>yyyy-MM-dd HH:mm:ss.SSS</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_TIME_FORMAT2() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	/** 新创建<strong>yyyyMMddHHmmss</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_TIME_FORMAT3() {
		return new SimpleDateFormat("yyyyMMddHHmmss");
	}

	/** 新创建<strong>yyyy-MM-dd HH:mm</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_TIME_FORMAT4() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	/** 新创建<strong>yyyy/MM/dd HH:mm</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_TIME_FORMAT5() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm");
	}

	/** 新创建<strong>yyyyMMdd</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_FORMAT1() {
		return new SimpleDateFormat("yyyyMMdd");
	}

	/** 新创建<strong>yyyy-MM-dd</strong>类型格式化对象 */
	private final static SimpleDateFormat DATE_FORMAT2() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	/** 新创建<strong>HHmmss</strong>类型格式化对象 */
	private final static SimpleDateFormat TIME_FORMAT() {
		return new SimpleDateFormat("HHmmss");
	}

	/** 日期字段变量，年 */
	public final static int field_year = 1;
	/** 日期字段变量，月 */
	public final static int field_month = 2;
	/** 日期字段变量：天 */
	public final static int field_day = 5;
	/** 日期字段变量：小时 */
	public final static int field_hour = 10;
	/** 日期字段变量：分钟 */
	public final static int field_minute = 12;
	/** 日期字段变量：秒 */
	public final static int field_second = 13;

	/**
	 * <p>
	 * 将日期字符串转换为 零点开始的日期(Date)对象
	 * </p>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午9:36:07
	 * @param dateStr
	 *            <code>String</String>	日期字符串,参数格式(yyyy-MM-dd)
	 * @return 参数非空情况返回指定字符串当天零点零分零秒时间，参数为空返回null
	 */
	public static Date dateStr2BeginDate(String dateStr) {
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				return DATE_TIME_FORMAT2().parse(dateStr + " 00:00:00.000");
			} catch (ParseException e) {
			}
		}
		return null;
	}

	/**
	 * 将日期字符串转换为 59分59秒999毫秒结束的日期(Date)对象
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @param dateStr
	 *            日期字符串参数格式(yyyy-MM-dd)
	 * @date 2016-4-22 上午9:36:07
	 * @return 参数非空返回指定字符串当天结束时间，参数为空返回null
	 */
	public static Date dateStr2EndDate(String dateStr) {
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				return DATE_TIME_FORMAT2().parse(dateStr + " 23:59:59.999");
			} catch (ParseException e) {
			}
		}
		return null;
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式字符串parse成日期对象
	 * 
	 * @param dateStr
	 *            <code>String</code> 解析的日期字符串，
	 *            格式为<strong>yyyy-MM-dd HH:mm:ss</strong>
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午9:36:07
	 * @return 异常返回null
	 */
	public static Date parseDateTime1(String dateStr) {
		try {
			return DATE_TIME_FORMAT1().parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将日期格式化成字符串，字符串格式形如：<strong>yyyy-MM-dd HH:mm:ss</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午10:30:21
	 * @param date
	 *            <code>java.util.Date</code> 日期
	 * @return 返回格式化字符串，参数为空时返回空字符串""
	 */
	public static String formatDateTime1(Date date) {
		if (date != null) {
			return DATE_TIME_FORMAT1().format(date);
		} else {
			return "";
		}
	}

	/**
	 * 日期时间解析，将给定字符串解析成日期时间，包含十分秒，且精确到毫秒，如：2015-06-15 15:22:25.452
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午10:23:32
	 * @param dateStr
	 *            日期时间字符串，格式：<strong>yyyy-MM-dd HH:mm:ss.SSS</strong>
	 * @return 异常返回null
	 */
	public static Date parseDateTime2(String dateStr) {
		try {
			return DATE_TIME_FORMAT2().parse(dateStr);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 格式化日期时间成字符串，格式：<strong>yyyy-MM-dd HH:mm:ss.SSS</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:40:13
	 * @param date
	 *            日期时间对象
	 * @return 返回格式化字符串，参数为空时返回空字符串""
	 */
	public static String formatDateTime2(Date date) {
		if (date != null) {
			return DATE_TIME_FORMAT2().format(date);
		} else {
			return "";
		}
	}

	/**
	 * 将日期字符串按照<strong>yyyyMMddHHmmss</strong>解析为日期对象，
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:04:29
	 * @param dateStr
	 *            日期字符串，格式必须为：<strong>yyyyMMddHHmmss</strong>
	 * @return 日期对象<code>java.util.Date</code>对象，异常时返回null
	 */
	public static Date parseDateTime3(String dateStr) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			return DATE_TIME_FORMAT3().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 格式化日期时间成字符串，格式：<strong>yyyyMMddHHmmss</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:40:13
	 * @param date
	 *            日期时间对象
	 * @return 返回格式化字符串，参数为空时返回空字符串""
	 */
	public static String formatDateTime3(Date date) {
		if (date != null) {
			return DATE_TIME_FORMAT3().format(date);
		} else {
			return "";
		}
	}

	/**
	 * 将日期字符串按照<strong>yyyy-MM-dd HH:mm</strong>解析为日期对象，
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:24:22
	 * @param dateStr
	 *            日期字符串，格式必须为：<strong>yyyy-MM-dd HH:mm</strong>
	 * @return 日期对象<code>java.util.Date</code>对象，异常时返回null
	 */
	public static Date parseDateTime4(String dateStr) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			return DATE_TIME_FORMAT4().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 格式化日期时间成字符串，格式：<strong>yyyy-MM-dd HH:mm</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:40:13
	 * @param date
	 *            日期时间对象
	 * @return 返回格式化字符串，参数为空时返回空字符串""
	 */
	public static String formatDateTime4(Date date) {
		if (date != null) {
			return DATE_TIME_FORMAT4().format(date);
		} else {
			return "";
		}
	}

	/**
	 * 将日期字符串按照<strong>yyyy/MM/dd HH:mm</strong>解析为日期对象，
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:33:22
	 * @param dateStr
	 *            日期字符串，格式必须为：<strong>yyyy/MM/dd HH:mm</strong>
	 * @return 日期对象<code>java.util.Date</code>对象，异常时返回null
	 */
	public static Date parseDateTime5(String dateStr) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			return DATE_TIME_FORMAT5().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 格式化日期时间成字符串，格式：<strong>yyyy/MM/dd HH:mm</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:40:13
	 * @param date
	 *            日期时间对象
	 * @return 返回格式化字符串，参数为空时返回空字符串""
	 */
	public static String formatDateTime5(Date date) {
		if (date != null) {
			return DATE_TIME_FORMAT5().format(date);
		} else {
			return "";
		}
	}

	/**
	 * 将日期格式化成字符串,字符串格式形如：<strong>yyyyMMdd</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午10:28:20
	 * @param date
	 *            <code>java.util.Date</code> 日期对象
	 * @return 返回格式化字符串，参数错误返回空字符串""
	 */
	public static String formatDate1(Date date) {
		if (date != null) {
			return DATE_FORMAT1().format(date);
		}
		return "";
	}

	/**
	 * 将给定日期字符串按照<strong>yyyyMMdd</strong>解析成日期对象
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:53:56
	 * @param dateStr
	 *            日期字符串，格式必须为：<strong>yyyyMMdd</strong>
	 * @return 日期时间对象，异常或参数为空是返回null
	 */
	public static Date parseDate1(String dateStr) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			return DATE_FORMAT1().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 将日期格式化成字符串,字符串格式形如：<strong>yyyy-MM-dd</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午10:28:20
	 * @param date
	 *            <code>java.util.Date</code> 日期对象
	 * @return 返回格式化字符串，参数错误返回空字符串""
	 */
	public static String formatDate2(Date date) {
		if (date != null) {
			return DATE_FORMAT2().format(date);
		}
		return "";
	}

	/**
	 * 将给定日期字符串按yyyy-MM-dd格式解析成日期对象
	 * 
	 * @author hg
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:02:01
	 * @param dateStr
	 *            日期字符串
	 * @return 解析后的<code>java.util.Date<code>对象，参数为空或解析异常返回null
	 */
	public static Date parseDate2(String dateStr) {
		try {
			if (StringUtils.isBlank(dateStr)) {
				return null;
			}
			return DATE_FORMAT2().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 将给定时间字符串按HHmmss格式解析成日期对象
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午11:58:48
	 * @param timeStr
	 * @return
	 */
	public static Date parseTime(String timeStr) {
		try {
			return TIME_FORMAT().parse(timeStr);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 将时间格式化成字符串,字符串格式形如：<strong>HHmmss</strong>
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 上午10:28:20
	 * @param date
	 *            <code>java.util.Date</code> 日期对象
	 * @return 返回格式化字符串，参数错误返回空字符串""
	 */
	public static String formatTime(Date date) {
		if (date != null) {
			return TIME_FORMAT().format(date);
		} else {
			return "";
		}
	}

	public static String formatDateTime(Date date, String formatStr) {
		if (date == null || formatStr == null || formatStr.trim().equals("")) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
		return dateFormat.format(date);
	}

	public static Date parseDateTime(String dateStr, String formatStr) {

		return null;
	}

	/**
	 * 验证给定日期时间字符串是否符合指定格式
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午12:04:30
	 * @param date
	 *            日期或时间字符串
	 * @param form
	 *            对应日期或时间格式
	 * @return 给定日期时间符合给定格式返回true，否则返回false
	 */
	public static boolean checkDate(String date, String form) {
		if (StringUtils.isBlank(date))
			return false;

		ParsePosition pos = new ParsePosition(0);
		DateFormat format = null;
		if (StringUtils.isBlank(form))
			format = new SimpleDateFormat("yyyy-MM-dd");
		else
			format = new SimpleDateFormat(form);
		// 严格解析时间格式
		format.setLenient(false);

		if (null == format.parse(date, pos))
			return false;
		// 如果开始索引不相等的话
		if (pos.getErrorIndex() > 0 || pos.getIndex() != date.length())
			return false;
		return true;
	}

	/**
	 * 日期时间对象先后比较
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @param baseDate
	 *            相比较日期的基准，可以为null，如果为null则和当前日期比较
	 * @param reqDate
	 *            需要比较的日期，不能为null
	 * @return 大于基准日期则返回true,否则返回false
	 */
	public static boolean dateCompare(Date baseDate, Date reqDate) {
		boolean theFlag = false;
		// if baseDate is null,set current date to baseDate
		if (baseDate == null) {
			baseDate = new Date();
		}
		if (reqDate.getTime() > baseDate.getTime()) {
			theFlag = true;
		}
		return theFlag;
	}

	/**
	 * 字符串形式日期时间比较,格式必须为<strong>yyyy-MM-dd HH:mm:ss</strong>形式
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @param baseDate
	 *            相比较日期的基准，可以为null或空，如果为null则和当前日期比较
	 * @param reqDate
	 *            需要比较的日期，不能为null或空
	 * @return 大于基准日期则返回true,否则返回false
	 */
	public static boolean dateCompare(String baseDate, String reqDate) {
		Date bDate = null;
		Date rDate;
		if (baseDate == null || baseDate.trim().equals("")) {
			bDate = new Date();
		} else {
			bDate = parseDateTime1(baseDate);
		}
		rDate = parseDateTime1(reqDate);
		return dateCompare(bDate, rDate);
	}

	/**
	 * 修改日期的指定字段值
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午1:45:53
	 * @param date
	 *            所要计算的日期
	 * @param field
	 *            需要改变的日期中的字段，候选值为本类中静态变量field-year等
	 * @param amount
	 *            更改的差值，允许负值
	 * @see #field_year
	 * @see #field_month
	 * @see #field_day
	 * @see #field_hour
	 * @see #field_minute
	 * @see #field_second
	 * @return 修改后的日期
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.add(field, amount);
		return instance.getTime();
	}

	/**
	 * 计算两日期之间所差的天数，小时，分钟，秒
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午2:31:57
	 * @param date1
	 *            比较的时间
	 * @param date2
	 *            比较的第二个时间
	 * @param field
	 *            要计算的差值字段,使用本类中静态变量field_day等
	 * @see #field_day
	 * @see #field_hour
	 * @see #field_minute
	 * @see #field_second
	 * @return 相差结果，如果第一个日期比第二个日期早，返回值为负
	 */
	public static int diff(Date date1, Date date2, int field) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long diff = time1 - time2;
		int result = 0;
		switch (field) {
		case field_day:
			result = (int) (diff / (1000 * 60 * 60 * 24));
			break;
		case field_hour:
			result = (int) (diff / (1000 * 60 * 60));
			break;
		case field_minute:
			result = (int) (diff / (1000 * 60));
			break;
		case field_second:
			result = (int) (diff / 1000f);
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * 判断日期所在年份是否为闰年
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午2:59:54
	 * @param date 给定的日期
	 * @return
	 */
	public static boolean isLeapYear(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 判断给定的年份是否为闰年
	 * 
	 * @author guotx
	 * @since hg-common-0.8
	 * @date 2016-4-22 下午3:00:44
	 * @param year
	 *            给定的年份
	 * @return 是闰年返回true，否则返回false
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}
	/**
	 * 获得本月第一天
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:10:54
	 */
	public static String forDateFirst(){
		//规定返回日期格式
		SimpleDateFormat sf= DATE_FORMAT2();
		Calendar calendar=Calendar.getInstance();
		Date theDate=calendar.getTime();
		GregorianCalendar gcLast=(GregorianCalendar)Calendar.getInstance();
		gcLast.setTime(theDate);
		//设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first=sf.format(gcLast.getTime());
		//打印本月第一天
		System.out.println(day_first);
		return day_first;
	}
	/**
	 * 本月最后一天
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:13:37
	 */
	public static String forDatelast(){
		//获取Calendar
		Calendar calendar=Calendar.getInstance();
		//设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(calendar.DATE));
		//设置日期格式
		SimpleDateFormat sf= DATE_FORMAT2();
		String ss=sf.format(calendar.getTime());
		System.out.println(ss);
		return ss;
	}
	/**
	 * 次月第一天
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:19:40 
	 * @throws Exception
	 */
	public static String getNextFirstMonthdate(){
		SimpleDateFormat format= DATE_FORMAT2();
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String ss = format.format(calendar.getTime());
		System.out.println("下个月第一天："+format.format(calendar.getTime()));
		return ss;
	}
	
	/**
	 * 次月最后一天
	 * @author admin
	 * @since hgfx-admin-common
	 * @date 2016-7-25 下午2:23:16 
	 * @throws Exception
	 */
	public static String getNextLastMonthdate(){
		SimpleDateFormat sf= DATE_FORMAT2();
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month+1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		System.out.println("下个月最后一天："+sf.format(calendar.getTime()));
		return sf.format(calendar.getTime());
	}
	
	/**
	 * 获取上月第一天日期
	 * 返回 yyyy-MM-dd
	 * */
	public static String getLastMonthFistDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return DATE_FORMAT2().format(calendar.getTime());
	}
	
	/**
	 * 获取上月最后一天日期
	 * 返回 yyyy-MM-dd
	 * */
	public static String getLastMonthEndDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return DATE_FORMAT2().format(calendar.getTime());
	}
	
}