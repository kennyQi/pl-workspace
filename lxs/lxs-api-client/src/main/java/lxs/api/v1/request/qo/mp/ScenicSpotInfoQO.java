package lxs.api.v1.request.qo.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class ScenicSpotInfoQO extends ApiPayload {

	/**
	 * 景区ID
	 */
	private String scenicSpotID;

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public static void main(String[] args) {
		ScenicSpotInfoQO scenicSpotInfoQO = new ScenicSpotInfoQO();
		scenicSpotInfoQO.setScenicSpotID("景区ID");
		System.out.println(JSON.toJSONString(scenicSpotInfoQO));
	}
}
