package hg.dzpw.pojo.command.merchant.policy;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

import java.util.List;

/**
 * @类功能说明：逻辑删除独立单票政策（景区后台）
 * @类修改者：
 * @修改日期：2015-2-10下午4:09:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-10下午4:09:48
 */
@SuppressWarnings("serial")
public class ScenicspotRemoveSingleTicketPolicyCommand extends DZPWMerchantBaseCommand {

	/**
	 * 独立单票政策ID
	 */
	private List<String> ticketPolicyId;

	public List<String> getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(List<String> ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

}
