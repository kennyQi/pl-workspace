package hg.log.po.clickrecord;

import hg.log.po.base.BaseLog;

public class ClickRecord extends BaseLog{
	
	/**
	 * 点击链接
	 */
	private String url;
	
	/**
	 * 点击类别(在下面定义常量)
	 * 
	 */
	private Integer type;
	
	/**
	 * 景点id
	 */
	private String scenicSpotId;
	
	/**
	 * 点击用户id（匿名点击无id）
	 */
	private String userId;

	/**
	 * 点击来源
	 */
	private String fromIP;
	
	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}
	
	public final static Integer CLICK_RECORD_SCENICSPOT_DETAIL = 1000;

	

}
