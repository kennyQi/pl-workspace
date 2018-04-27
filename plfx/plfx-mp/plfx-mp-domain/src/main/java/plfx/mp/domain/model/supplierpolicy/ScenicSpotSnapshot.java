package plfx.mp.domain.model.supplierpolicy;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 景点快照
 * 
 * @author zhurz
 */
@Embeddable
public class ScenicSpotSnapshot implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 景点ID
	 */
	@Column(name = "SCENIC_SPOTS_ID", length = 64)
	private String scenicSpotsId;

	/**
	 * 同程景点ID
	 */
	@Column(name = "TC_SCENIC_SPOTS_ID", length = 64)
	private String tcScenicSpotsId;
	
	/**
	 * 景点名称
	 */
	@Column(name = "TC_SCENIC_SPOTS_NAME", length = 64)
	private String tcScenicSpotsName;
	
	/**
	 * 所在省代码
	 */
	@Column(name = "PROVINCE_CODE", length = 64)
	private String provinceCode;
	
	/**
	 * 所在城市代码
	 */
	@Column(name = "CITY_CODE", length = 64)
	private String cityCode;
	
	/**
	 * 所在区域代码
	 */
	@Column(name = "AREA_CODE", length = 64)
	private String areaCode;
	
	/**
	 * 所在省名称
	 */
	@Column(name = "PROVINCE_NAME", length = 64)
	private String provinceName;
	
	/**
	 * 所在市名称
	 */
	@Column(name = "CITY_NAME", length = 64)
	private String cityName;
	
	/**
	 * 所在区名称
	 */
	@Column(name = "AREA_NAME", length = 64)
	private String areaName;

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

	public String getScenicSpotsId() {
		return scenicSpotsId;
	}

	public void setScenicSpotsId(String scenicSpotsId) {
		this.scenicSpotsId = scenicSpotsId;
	}

}
