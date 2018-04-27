package hg.service.ad.pojo.dto.ad;

import hg.service.ad.base.BaseDTO;


@SuppressWarnings("serial")
public class AdPositionDTO extends BaseDTO{
	/**
	 * 广告位的基本信息
	 */
	private AdPositionBaseInfoDTO baseInfo;
	/**
	 * 广告位的展示信息
	 */
	private AdPositionShowInfoDTO showInfo;
	
	public AdPositionBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(AdPositionBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}
	public AdPositionShowInfoDTO getShowInfo() {
		return showInfo;
	}
	public void setShowInfo(AdPositionShowInfoDTO showInfo) {
		this.showInfo = showInfo;
	}

}
