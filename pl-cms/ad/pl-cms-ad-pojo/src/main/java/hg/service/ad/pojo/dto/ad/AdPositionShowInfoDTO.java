package hg.service.ad.pojo.dto.ad;

import hg.service.ad.base.BaseDTO;

@SuppressWarnings("serial")
public class AdPositionShowInfoDTO extends BaseDTO{
	/**
	 * 显示条数
	 */
	private Integer showNo;
	/**
	 * 加载条数
	 */
	private Integer loadNo;
	/**
	 * 是否切换
	 */
	private Integer changeSpeedSecond;
	
	public Integer getShowNo() {
		return showNo;
	}
	public void setShowNo(Integer showNo) {
		this.showNo = showNo;
	}
	public Integer getLoadNo() {
		return loadNo;
	}
	public void setLoadNo(Integer loadNo) {
		this.loadNo = loadNo;
	}
	public Integer getChangeSpeedSecond() {
		return changeSpeedSecond;
	}
	public void setChangeSpeedSecond(Integer changeSpeedSecond) {
		this.changeSpeedSecond = changeSpeedSecond;
	}
	
}
