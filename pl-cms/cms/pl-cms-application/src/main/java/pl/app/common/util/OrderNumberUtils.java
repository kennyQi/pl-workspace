package pl.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;

public class OrderNumberUtils {

	public final static SimpleDateFormat DATE_FORMAT(){return new SimpleDateFormat("yyyyMMddHHmmssSSS");}

	/**
	 * 生成订单流水号
	 * 
	 * @return
	 */
	public static String createOrderNo() {
		StringBuilder sb = new StringBuilder(DATE_FORMAT().format(new Date()));
		for (int i = 0; i < 13; i++) {
			sb.append(RandomUtils.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 13; i++) {
			System.out.println(createOrderNo());
		}
	}
}
