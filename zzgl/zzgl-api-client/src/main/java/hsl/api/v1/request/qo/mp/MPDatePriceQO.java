package hsl.api.v1.request.qo.mp;

import hsl.api.base.ApiPayload;

/**
 * 门票价格日历查询
 * 
 * @author yuxx
 * 
 */
public class MPDatePriceQO extends ApiPayload {

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
