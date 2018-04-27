package slfx.mp.pojo.dto.supplierpolicy;

import slfx.mp.pojo.dto.EmbeddDTO;

/**
 * 景点快照
 * 
 * @author zhurz
 */
public class ScenicSpotSnapshotDTO extends EmbeddDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 平台景点ID
	 */
	private String scenicSpotsId;
	
	/**
	 * 同程景点ID
	 */
	private String tcScenicSpotsId;
	
	/**
	 * 景点名称
	 */
	private String tcScenicSpotsName;
	
	/**
	 * 所在省代码
	 */
	private String provinceCode;
	
	/**
	 * 所在省
	 */
	private String provinceName;
	
	/**
	 * 所在城市代码
	 */
	private String cityCode;
	
	/**
	 * 所在市
	 */
	private String cityName;
	
	/**
	 * 所在区域代码
	 */
	private String areaCode;
	
	/**
	 * 所在区
	 */
	private String areaName;

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTcScenicSpotsId() {
		return tcScenicSpotsId;
	}

	public void setTcScenicSpotsId(String tcScenicSpotsId) {
		this.tcScenicSpotsId = tcScenicSpotsId;
	}

	public String getTcScenicSpotsName() {
		return tcScenicSpotsName;
	}

	public void setTcScenicSpotsName(String tcScenicSpotsName) {
		this.tcScenicSpotsName = tcScenicSpotsName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getScenicSpotsId() {
		return scenicSpotsId;
	}

	public void setScenicSpotsId(String scenicSpotsId) {
		this.scenicSpotsId = scenicSpotsId;
	}

}
