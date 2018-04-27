package hg.service.ad.pojo.dto.ad;
import hg.service.ad.base.BaseDTO;

@SuppressWarnings("serial")
public class AdDTO extends BaseDTO {
	/**
	 * 广告位外键
	 */
	private AdPositionDTO position;
	/**
	 * 广告基本信息
	 */
	public AdBaseInfoDTO adBaseInfo;
	/**
	 * 广告状态
	 */
	public AdStatusDTO adStatus;
	
	public AdPositionDTO getPosition() {
		return position;
	}
	public void setPosition(AdPositionDTO position) {
		this.position = position;
	}
	public AdBaseInfoDTO getAdBaseInfo() {
		return adBaseInfo;
	}
	public void setAdBaseInfo(AdBaseInfoDTO adBaseInfo) {
		this.adBaseInfo = adBaseInfo;
	}
	public AdStatusDTO getAdStatus() {
		return adStatus;
	}
	public void setAdStatus(AdStatusDTO adStatus) {
		this.adStatus = adStatus;
	}
	
}