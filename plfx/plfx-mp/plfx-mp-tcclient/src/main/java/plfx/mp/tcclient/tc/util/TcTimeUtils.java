package plfx.mp.tcclient.tc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TcTimeUtils {
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 获取当前时间格式为yyyy-MM-dd HH:mm:ss.SSS
	 * @return
	 */
	public static String getCurrentTime(){
		try{
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			String reqTime = sdf.format(date);
			return reqTime;			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
