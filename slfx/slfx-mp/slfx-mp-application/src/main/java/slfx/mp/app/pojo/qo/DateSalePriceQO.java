package slfx.mp.app.pojo.qo;

import hg.common.component.BaseQo;

public class DateSalePriceQO extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策id 必填
	 */
	private String policyId;

	/**
	 * 经销商id(SPI层需要)
	 */
	private String dealerId;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

}
