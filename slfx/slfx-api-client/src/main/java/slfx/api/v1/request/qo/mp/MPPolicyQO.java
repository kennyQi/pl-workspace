package slfx.api.v1.request.qo.mp;

import slfx.api.base.ApiPayload;

/**
 * 景区政策查询
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPPolicyQO extends ApiPayload {

	/**
	 * 景区id 必填
	 */
	private String scenicSpotId;
	/**
	 * 景区政策ID
	 */
	private String policyId;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

}
