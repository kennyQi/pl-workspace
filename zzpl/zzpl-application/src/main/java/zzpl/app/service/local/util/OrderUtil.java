package zzpl.app.service.local.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OrderUtil {
	/**
	 * 
	 * @param date 生产订单号时的日期
	 * @param sequence 订单流水号
	 * @param source 订单来源：0来自移动端，1来自pc端；
	 * @param type 订单类型：0机票，1门票，2线路，3酒店；
	 * @return
	 */
	public static String createOrderNo(Date date, int sequence, int type, int source) {
		//年份按照A-Z取一位，从14年开始
		int baseYear = 51;
		Calendar calendar = Calendar.getInstance();
		
		if (date == null) date = new Date();
		calendar.setTime(date);
		
		int year = calendar.get(Calendar.YEAR) + baseYear - 2000;
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		
		return String.format("%c%X%02d%02d%02d%02d%d%d%04d", year, month, day, hours, minutes, seconds, source, type, sequence);
	}
	
	/**
	 * 从身份证号码提取日期
	 * @param idCardNo
	 * @return
	 */
	public static String getBirthdayFromIdCardNo(String idCardNo) {
		return StringUtils.isBlank(idCardNo) || idCardNo.length() < 14 ? "" :
			idCardNo.substring(6, 10) + "-" + idCardNo.substring(10, 12) + "-" + idCardNo.substring(12, 14);
	}
	
	public static void main(String[] args) {
//		System.out.println(OrderUtil.getBirthdayFromIdCardNo("33078119880601"));
		System.out.println(String.format("%X,%X", 122, 107));
	}
	
}
