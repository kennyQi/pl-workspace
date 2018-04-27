package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslMPPolicyQO extends BaseQo {

	/**
	 * 景区id 
	 */
	private String scenicSpotId;
	
	/**
	 * 政策id
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
