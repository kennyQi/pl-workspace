package hg.dzpw.pojo.command.platform.policy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：改变联票启用状态（运营后台）
 * @类修改者：
 * @修改日期：2015-3-17下午4:53:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-17下午4:53:57
 */
@SuppressWarnings("serial")
public class PlatformChangeGroupTicketPolicyStatusCommand extends DZPWPlatformBaseCommand {

	/**
	 * 独立单票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * true=启用（已发布状态） 
	 * false=禁用（下架状态）
	 */
	private Boolean active;
	
	public PlatformChangeGroupTicketPolicyStatusCommand(){}
	
	public PlatformChangeGroupTicketPolicyStatusCommand(String ticketPolicyId, Boolean active){
		this.active = active;
		this.ticketPolicyId = ticketPolicyId;
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
