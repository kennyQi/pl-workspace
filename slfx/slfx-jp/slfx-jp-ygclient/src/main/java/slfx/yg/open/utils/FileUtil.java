package slfx.yg.open.utils;

import hg.log.util.HgLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @类功能说明：文件工具类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:18:49
 * @版本：V1.0
 *
 */
public class FileUtil {

	public static String readTxtFile(String filePath) {
		StringBuffer strBuff = new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					strBuff.append(lineTxt + "\n");

				}
				read.close();
			} else {
				HgLogger.getInstance().info("qiuxianxiang", "FileUtil->readTxtFile->[找不到指定的文件]");
			}
		} catch (Exception e) {
			HgLogger.getInstance().info("qiuxianxiang", "FileUtil->readTxtFile->[读取文件内容出错]"+HgLogger.getStackTrace(e));
		}

		return strBuff.toString();
	}

	public static YGXmlUtil ygXmlUtil = new YGXmlUtil();

	public static void main(String[] args) throws Exception {

		Calendar cale = Calendar.getInstance();
		cale.setTime(new Date());
		
		cale.setTime(new Date((System.currentTimeMillis()-16546313132l)));
		//
		// System.out.println(cale.getWeekYear());
		// System.out.println(cale.get(Calendar.WEEK_OF_MONTH));
		int year = cale.get(Calendar.YEAR);
		System.out.println("year = " + year); // 当前月 Calendar.MONTH从0开始
		int month = (cale.get(Calendar.MONTH)) + 1;
		System.out.println("month = " + month); // 当前月的第几天：即当前日 int day_of_month =
		int day_of_month = cale.get(Calendar.DAY_OF_MONTH);
		System.out.println("day_of_month"+day_of_month);
		
		int dates = cale.get(Calendar.DATE);   
		System.out.println("dates = "+dates);   
		  //System.out.println(day_of_month);    
		  // 当前时：HOUR_OF_DAY-24小时制   
		  int hour24 = cale.get(Calendar.HOUR_OF_DAY);   // HOUR-12小时制   
		  int hour12 = cale.get(Calendar.HOUR);   
		  System.out.println("hour24 = "+ hour24);   
		  System.out.println("hour12 = "+ hour12);    
		  // 当前分   
		  int minute = cale.get(Calendar.MINUTE);   // 当前秒   
		  int second = cale.get(Calendar.SECOND);   
		  System.out.println("minute="+minute);   
		  System.out.println("second="+second);    
		  // 星期几 Calendar.DAY_OF_WEEK用数字（1~7）表示（星期日~星期六）   
		  
		  
		   
		   
		  int day_of_week =  cale.get(Calendar.DAY_OF_WEEK) - 1;   
		  System.out.println("day_of_week = "+day_of_week);    
		  // 当前年的第几周  
			int week_of_year =  cale.get(Calendar.WEEK_OF_YEAR);  
			System.out.println("week_of_year = " + week_of_year);    
		  // //当前月的星期数   // int week_of_month = cale.get(Calendar.WEEK_OF_MONTH);   //        
		  // //当前月中的第几个星期   
		  // int day_of_week_in_month = cale.get(Calendar.DAY_OF_WEEK_IN_MONTH);   //        
		  // //当前年的第几天   // int day_of_year = cale.get(Calendar.DAY_OF_YEAR);  } }   
	}
}
