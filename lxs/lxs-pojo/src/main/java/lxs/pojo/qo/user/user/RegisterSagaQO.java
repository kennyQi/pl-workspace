package lxs.pojo.qo.user.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class RegisterSagaQO extends BaseQo {
	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 流程id
	 */
	private String sagaid;
	
	public String getSagaid() {
		return sagaid;
	}

	public void setSagaid(String sagaid) {
		this.sagaid = sagaid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
