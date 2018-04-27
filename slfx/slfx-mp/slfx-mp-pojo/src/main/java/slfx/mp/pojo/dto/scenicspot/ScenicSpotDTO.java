package slfx.mp.pojo.dto.scenicspot;

import java.util.ArrayList;
import java.util.List;

import slfx.mp.pojo.dto.BaseMpDTO;

/**
 * 旅游景点
 */
public class ScenicSpotDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 同程景区信息
	 */
	private TCScenicSpotsDTO tcScenicSpotInfo;
	/**
	 * 景点基本信息
	 */
	private ScenicSpotsBaseInfoDTO scenicSpotBaseInfo;
	/**
	 * 景点详情
	 */
	private String detail;
	/**
	 * 景点地理信息(省市区经纬度等)
	 */
	private ScenicSpotsGeographyInfoDTO scenicSpotGeographyInfo;
	/**
	 * 景区图片
	 */
	private List<ImageSpecDTO> images = new ArrayList<ImageSpecDTO>();

	public TCScenicSpotsDTO getTcScenicSpotInfo() {
		return tcScenicSpotInfo;
	}

	public void setTcScenicSpotInfo(TCScenicSpotsDTO tcScenicSpotInfo) {
		this.tcScenicSpotInfo = tcScenicSpotInfo;
	}

	public ScenicSpotsBaseInfoDTO getScenicSpotBaseInfo() {
		return scenicSpotBaseInfo;
	}

	public void setScenicSpotBaseInfo(ScenicSpotsBaseInfoDTO scenicSpotBaseInfo) {
		this.scenicSpotBaseInfo = scenicSpotBaseInfo;
	}

	public ScenicSpotsGeographyInfoDTO getScenicSpotGeographyInfo() {
		return scenicSpotGeographyInfo;
	}

	public void setScenicSpotGeographyInfo(
			ScenicSpotsGeographyInfoDTO scenicSpotGeographyInfo) {
		this.scenicSpotGeographyInfo = scenicSpotGeographyInfo;
	}

	public List<ImageSpecDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageSpecDTO> images) {
		this.images = images;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}