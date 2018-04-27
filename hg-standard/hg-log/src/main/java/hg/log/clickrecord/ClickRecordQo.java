package hg.log.clickrecord;

import hg.log.base.BaseLogQo;

public class ClickRecordQo extends BaseLogQo{

	private String userId;
	
	private String scenicSpotId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	
	
}
