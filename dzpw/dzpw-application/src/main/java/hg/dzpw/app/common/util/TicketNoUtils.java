package hg.dzpw.app.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import hg.dzpw.domain.model.ticket.GroupTicket;

/**
 * @类功能说明：门票票号工具
 * @类修改者：
 * @修改日期：2015-5-19上午10:38:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-5-19上午10:38:09
 */
public class TicketNoUtils {

	/**
	 * @方法功能说明：生成票号
	 * @修改者名字：zhurz
	 * @修改时间：2015-5-19上午10:32:26
	 * @修改内容：
	 * @参数：@param ticketType
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String generateTicketNo(Integer ticketType) {
		
		// 默认为单票类型
		if (ticketType == null)
			ticketType = GroupTicket.GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT;

		Calendar calendar = Calendar.getInstance();

		String[] formats = new String[] { "%09d",// 随机码
				"%s",// 年
				"%x",// 类型
				"%02d",// 时
				"%02d",// 日
				"%02d",// 秒
				"%x",// 月
				"%02d"// 分
		};
		
		Object[] formatValues = new Object[] {
				new Random().nextInt(1000000000),// 随机码
				Integer.toString(calendar.get(Calendar.YEAR) - 2015 + 10, 36),// 年
				ticketType + 9,// 类型
				calendar.get(Calendar.HOUR),// 时
				calendar.get(Calendar.DATE),// 日
				calendar.get(Calendar.SECOND),// 秒
				calendar.get(Calendar.MONTH) + 1,// 月
				calendar.get(Calendar.MINUTE) // 分
		};
		
		StringBuilder sb = new StringBuilder();
		for (String format : formats)
			sb.append(format);

		return String.format(sb.toString(), formatValues).toUpperCase();
	}
	
	
	/**
	 * @方法功能说明：生成GroupTicket
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-14下午1:30:20
	 * @参数：@param ticketType
	 * @参数：@return
	 * @return:String
	 */
//	public static String getGroupTicketNo(Integer ticketType) {
//		
//	}

	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().getTimeInMillis());
		System.out.println(TicketNoUtils.generateTicketNo(2));
		
		System.out.println(String.format("LPYY%1$08d", 5));
			
//		System.out.println(String.format(new Random().nextInt(999999999)+"%03d", args));
//		Random random = new Random();
//		System.out.println("票号生成");
//		for (int i = 0; i < 100; i++){
//			String xx = generateTicketNo(random.nextInt(2) + 1);
//			System.out.println(String.format(xx + "%03d", ++i));
//		}
	}
}
