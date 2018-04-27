package hsl.pojo.dto.ad;

import hsl.pojo.dto.BaseDTO;

public class HslAdPositionDTO extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 广告位的基本信息
	 */
	private HslAdPositionBaseInfoDTO baseInfo;
	/**
	 * 广告位的展示信息
	 */
	private HslAdPositionShowInfoDTO showInfo;
	public HslAdPositionBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(HslAdPositionBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}
	public HslAdPositionShowInfoDTO getShowInfo() {
		return showInfo;
	}
	public void setShowInfo(HslAdPositionShowInfoDTO showInfo) {
		this.showInfo = showInfo;
	}
	
}
