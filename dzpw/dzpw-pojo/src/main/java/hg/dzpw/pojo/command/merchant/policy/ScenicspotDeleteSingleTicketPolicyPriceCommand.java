package hg.dzpw.pojo.command.merchant.policy;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：景区端删除门票政策价格日历
 * @类修改者：
 * @修改日期：2015-3-5下午3:51:44
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-5下午3:51:44
 */
@SuppressWarnings("serial")
public class ScenicspotDeleteSingleTicketPolicyPriceCommand extends DZPWMerchantBaseCommand {

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
