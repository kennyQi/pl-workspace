package hsl.h5.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	/**
	 * 
	 * @方法功能说明：去掉日期,后面的时分秒
	 * @修改者名字：hgg
	 * @修改时间：2015年9月29日下午1:55:16
	 * @修改内容：
	 * @参数：@param date
	 * @参数：@return
	 * @return:Date
	 * @throws
	 */
	public static String getYearMonthDay(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String StrDate = sdf.format(date);
		
		return StrDate;
	}
	
   public static Date simpleYearMonthDay(String StrDate){
    	if(StringUtils.isNotBlank(StrDate)){
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	try {
    			return  sdf.parse(StrDate);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    	}
    	return null;
    }
}
