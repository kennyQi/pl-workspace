package pl.cms.domain.entity.scenic;

import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * 景点地理信息(省市区经纬度等)
 * 
 * @author Administrator
 */
@Embeddable
@SuppressWarnings("serial")
public class ScenicGeographyInfo implements Serializable {

	/**
	 * 景区地址
	 */
	@Column(name = "GEO_ADDRESS", length = 1024)
	private String address;

	/**
	 * 所在省
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GEO_PROVINCE_ID")
	private Province province;

	/**
	 * 所在城市
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GEO_CITY_ID")
	private City city;

	/**
	 * 经度
	 */
	@Transient
	private Double longitude;

	/**
	 * 纬度
	 */
	@Transient
	private Double latitude;

	/**
	 * 交通指南
	 */
	@Column(name = "GEO_TRAFFIC", length = 4000)
	private String traffic;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

}