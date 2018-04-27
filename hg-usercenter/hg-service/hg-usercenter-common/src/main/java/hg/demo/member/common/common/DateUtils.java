package hg.demo.member.common.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/**
	 * @param d "2008-10-13 15:20:25"
	 * @return
	 */
	public static Date format(String d, String f) {
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		try {
			return sdf.parse(d);
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
	
	public static Date format(String d) {
		return format(d, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * @param date
	 * @param timeout
	 */
	public static long longtime(Date date){
		Calendar c = Calendar.getInstance();
		long now = c.getTimeInMillis();
		c.setTime(date);
		long lastly = c.getTimeInMillis();
		return now - lastly;
	}

}
