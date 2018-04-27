package plfx.mp.domain.model.scenicspot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.mp.domain.model.M;

/**
 * 景点地理信息(省市区经纬度等)
 * 
 * @author Administrator
 */
@Embeddable
public class ScenicSpotsGeographyInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 景区地址
	 */
	@Column(name = "ADDRESS", length = 512)
	private String address;
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
	 * 经度
	 */
	@Column(name = "LONGITUDE", columnDefinition = M.DOUBLE_COLUM)
	private Double longitude;
	/**
	 * 纬度
	 */
	@Column(name = "LATITUDE", columnDefinition = M.DOUBLE_COLUM)
	private Double latitude;
	
	/**
	 * 交通指南
	 */
	@Column(name = "TRAFFIC", columnDefinition = M.TEXT_COLUM)
	private String traffic;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

}