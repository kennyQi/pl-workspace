package hg.dzpw.app.common.util;

import hg.common.util.DateUtil;
import java.util.Date;

/**
 * @类功能说明：日期工具类
 * @类修改者：
 * @修改日期：2014-12-2上午10:20:28
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-2上午10:20:28
 */
public class DateUtils extends DateUtil {

	/** 一秒毫秒数 */
	public final static long SECOND_MILLISECOND = 1000;
	/** 一分钟毫秒数 */
	public final static long MINUTE_MILLISECOND = 60 * SECOND_MILLISECOND;
	/** 一小时毫秒数 */
	public final static long HOUR_MILLISECOND = 60 * MINUTE_MILLISECOND;
	/** 一天毫秒数 */
	public final static long DATE_MILLISECOND = 24 * HOUR_MILLISECOND;
	
	/**
	 * @方法功能说明：计算两个日期之间的天数，多余的时间如果不足一天，则按一天算。
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-2上午10:19:46
	 * @修改内容：
	 * @参数：@param date1
	 * @参数：@param date2
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public static int days(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		long time = Math.abs(date2.getTime() - date1.getTime());
		long date = time / DATE_MILLISECOND + (time % DATE_MILLISECOND > 0 ? 1 : 0);
		date = date == 0 ? 1 : date;
		return Long.valueOf(date).intValue();
	}
}