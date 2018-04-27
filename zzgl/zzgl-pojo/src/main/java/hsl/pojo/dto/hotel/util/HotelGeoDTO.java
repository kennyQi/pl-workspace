package hsl.pojo.dto.hotel.util;

import hsl.pojo.dto.BaseDTO;

import java.util.List;
/**
 * 
 * @类功能说明：商圈
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-7-1下午4:59:45
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@SuppressWarnings("serial")
public class HotelGeoDTO extends BaseDTO{
	private String country;
	private String provinceName;
	private String provinceId;
	private String cityName;
	private String cityCode;
	private List<DistrictDTO> districts;
	private List<CommericalLocationDTO> commericalLocations;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public List<DistrictDTO> getDistricts() {
		return districts;
	}
	public void setDistricts(List<DistrictDTO> districts) {
		this.districts = districts;
	}
	public List<CommericalLocationDTO> getCommericalLocations() {
		return commericalLocations;
	}
	public void setCommericalLocations(List<CommericalLocationDTO> commericalLocations) {
		this.commericalLocations = commericalLocations;
	}
}
