package plfx.mp.command.admin;

import hg.common.component.BaseCommand;

/**
 * 发布并起用平台政策
 * 
 * @author zhurz
 */
public class StartPlatformPolicyCommand extends BaseCommand {

	/**
	 * 政策ID
	 */
	private String salePolicyId;

	public String getSalePolicyId() {
		return salePolicyId;
	}

	public void setSalePolicyId(String salePolicyId) {
		this.salePolicyId = salePolicyId;
	}

}
