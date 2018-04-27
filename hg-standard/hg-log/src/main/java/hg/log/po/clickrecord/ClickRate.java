package hg.log.po.clickrecord;

import hg.log.po.base.BaseLog;

/**
 * 景点点击量
 * @author liujz
 *
 */
public class ClickRate extends BaseLog{

	/**
	 * 景区id
	 */
	private String scenicSpotId;
	
	/**
	 * 点击量
	 */
	private Long clickAmount;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Long getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(Long clickAmount) {
		this.clickAmount = clickAmount;
	}
	
	
}
