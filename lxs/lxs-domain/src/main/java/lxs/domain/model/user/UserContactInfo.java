package lxs.domain.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserContactInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "MOBILE", length = 11)
	private String mobile;

	@Column(name = "EMAIL", length = 64)
	private String email;

	@Column(name = "IM", length = 64)
	private String im;

	/**
	 * 省份id
	 */
	@Column(name = "PROVINCE_ID", length = 64)
	private String provinceId;
	/**
	 * 省份名称
	 */
	@Column(name = "PROVINCE_NAME", length = 64)
	private String provinceName;

	/**
	 * 城市id
	 */
	@Column(name = "CITY_ID", length = 64)
	private String cityId;

	/**
	 * 城市名称
	 */
	@Column(name = "CITY_NAME", length = 64)
	private String cityName;

	/**
	 * 区县id
	 */
	@Column(name = "AREA_ID", length = 64)
	private String areaId;

	/**
	 * 区县名称
	 */
	@Column(name = "AREA_NAME", length = 64)
	private String areaName;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
