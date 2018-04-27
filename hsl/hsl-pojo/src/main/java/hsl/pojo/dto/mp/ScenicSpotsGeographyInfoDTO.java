package hsl.pojo.dto.mp;

import java.io.Serializable;

/**
 * 景点地理信息(省市区经纬度等)
 * 
 * @author Administrator
 */
public class ScenicSpotsGeographyInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 景区地址
	 */
	private String address;
	/**
	 * 所在省代码
	 */
	private String provinceCode;
	/**
	 * 所在城市代码
	 */
	private String cityCode;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	
	/**
	 * 交通指南
	 */
	private String traffic;

	/**
	 * 省名
	 */
	private String provinceName;
	
	/**
	 * 市名
	 */
	private String cityName;
	
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
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

}