package lxs.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class ScenicSpotPicQO extends BaseQo {

	private String scenicSpotID;

	private Integer versionNO;
	
	/**大于*/
	public final static Integer GREATER_THAN = 1;
	/**小于*/
	public final static Integer LESS_THAN = -1;
	
	private Integer versionType=0;

	public Integer getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(Integer versionNO) {
		this.versionNO = versionNO;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public Integer getVersionType() {
		return versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	
}
