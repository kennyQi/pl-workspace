package plfx.jp.app.common.util;

/**
 * @类功能说明：时间工具
 * @类修改者：
 * @修改日期：2015-7-17上午10:48:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-17上午10:48:02
 */
public class TimeUtils {
	
	/**
	 * @方法功能说明：将HH:mm转为分钟
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-17上午10:47:35
	 * @修改内容：
	 * @参数：@param timeStr
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	public static int convertMinutes(String timeStr) {
		try {
			String[] times = timeStr.trim().split(":");
			return Integer.valueOf(times[0]) * 60 + Integer.valueOf(times[1]);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(convertMinutes("8:23"));
	}
}
