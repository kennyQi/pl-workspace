package hg.dzpw.pojo.command.platform.policy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：运营端删除联票政策价格日历
 * @类修改者：
 * @修改日期：2015-3-18下午4:36:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-18下午4:36:26
 */
@SuppressWarnings("serial")
public class PlatformDeleteGroupTicketPolicyPriceCommand extends DZPWPlatformBaseCommand {

	/**
	 * 价格日历ID
	 */
	private String calendarId;

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

}
