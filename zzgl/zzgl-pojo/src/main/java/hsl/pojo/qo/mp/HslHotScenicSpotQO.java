package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslHotScenicSpotQO extends BaseQo{

	/**
	 * 平台景点id
	 */
	private String scenicSpotId;
	
	private Boolean isOpen;
	
	/**
	 * 热门景点id
	 */
	private String id;
	
	/**
	 * 景点名称
	 */
	private String scenicSpotName;
	
	/**
	 * 是否支持景区名称模糊查询
	 */
	private Boolean scenicSpotNameIsLike=true;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Boolean getScenicSpotNameIsLike() {
		return scenicSpotNameIsLike;
	}

	public void setScenicSpotNameIsLike(Boolean scenicSpotNameIsLike) {
		this.scenicSpotNameIsLike = scenicSpotNameIsLike;
	}
	
	
}
