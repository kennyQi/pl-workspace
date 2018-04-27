package hsl.pojo.dto.mp;

import hsl.pojo.dto.ad.HslAdDTO;
public class PCHotScenicSpotDTO extends HslAdDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 关联的景区
	 */
	private ScenicSpotDTO scenicSpot;
	/**
	 * 广告ID
	 */
	private String adId;
	public ScenicSpotDTO getScenicSpot() {
		return scenicSpot;
	}
	public void setScenicSpot(ScenicSpotDTO scenicSpot) {
		this.scenicSpot = scenicSpot;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	
}
