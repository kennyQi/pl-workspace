package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslMPDatePriceQO extends BaseQo {
	/**
	 * 政策id 必填
	 */
	private String policyId;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

}
