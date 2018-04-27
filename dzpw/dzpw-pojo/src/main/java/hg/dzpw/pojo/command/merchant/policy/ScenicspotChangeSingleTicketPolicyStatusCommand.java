package hg.dzpw.pojo.command.merchant.policy;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：改变独立单票启用状态（景区后台）
 * @类修改者：
 * @修改日期：2015-2-10下午4:09:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-10下午4:09:48
 */
@SuppressWarnings("serial")
public class ScenicspotChangeSingleTicketPolicyStatusCommand extends DZPWMerchantBaseCommand {

	/**
	 * 独立单票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * true=启用（已发布状态） 
	 * false=禁用（下架状态）
	 */
	private Boolean active;
	
	public ScenicspotChangeSingleTicketPolicyStatusCommand(){}
	
	public ScenicspotChangeSingleTicketPolicyStatusCommand(String ticketPolicyId, Boolean active, String scenicSpotId){
		this.ticketPolicyId = ticketPolicyId;
		this.active = active;
		this.setScenicSpotId(scenicSpotId);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

}
