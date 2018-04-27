package hsl.pojo.dto.ad;

import hsl.pojo.dto.BaseDTO;

public class HslAdDTO extends BaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 广告位外键
	 */
	private HslAdPositionDTO position;
	/**
	 * 广告基本信息
	 */
	public HslAdBaseInfoDTO adBaseInfo;
	/**
	 * 广告状态
	 */
	public HslAdStatusDTO adStatus;
	
	public HslAdPositionDTO getPosition() {
		return position;
	}
	public void setPosition(HslAdPositionDTO position) {
		this.position = position;
	}
	public HslAdBaseInfoDTO getAdBaseInfo() {
		return adBaseInfo;
	}
	public void setAdBaseInfo(HslAdBaseInfoDTO adBaseInfo) {
		this.adBaseInfo = adBaseInfo;
	}
	public HslAdStatusDTO getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(HslAdStatusDTO adStatus) {
		this.adStatus = adStatus;
	}
	
}
