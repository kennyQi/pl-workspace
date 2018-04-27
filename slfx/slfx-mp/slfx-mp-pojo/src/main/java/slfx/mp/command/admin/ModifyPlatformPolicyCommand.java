package slfx.mp.command.admin;

import hg.common.component.BaseCommand;

/**
 * 平台政策修改
 * 
 * @author yuxx
 */
public class ModifyPlatformPolicyCommand extends BaseCommand {
	/**
	 * 政策ID
	 */
	private String policyId;
	
	/**
	 * 政策状态
	 */
	private Integer state;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	

}
