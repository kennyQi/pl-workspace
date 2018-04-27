package hsl.api.v1.request.qo.mp;

import hsl.api.base.ApiPayload;

/**
 * 景区政策查询
 * 
 * @author yuxx
 * 
 */
public class MPPolicyQO extends ApiPayload {

	/**
	 * 景区id 必填
	 */
	private String scenicSpotId;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

}
