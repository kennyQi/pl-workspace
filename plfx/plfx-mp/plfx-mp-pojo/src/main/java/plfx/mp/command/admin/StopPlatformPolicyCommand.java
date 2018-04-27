package plfx.mp.command.admin;

import hg.common.component.BaseCommand;

/**
 * 平台政策禁用
 * 
 * @author yuxx
 */
public class StopPlatformPolicyCommand extends BaseCommand {

	/**
	 * 政策ID
	 */
	private String policyId;

	/**
	 * 取消原因
	 */
	private String cancelInfo;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getCancelInfo() {
		return cancelInfo;
	}

	public void setCancelInfo(String cancelInfo) {
		this.cancelInfo = cancelInfo;
	}

}
