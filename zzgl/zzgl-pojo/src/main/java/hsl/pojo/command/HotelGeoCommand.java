package hsl.pojo.command;




import hsl.pojo.dto.hotel.util.CommericalLocationDTO;
import hsl.pojo.dto.hotel.util.DistrictDTO;

import java.util.List;



public class HotelGeoCommand {
	private String country;
	private String provinceName;
	private String provinceId;
	private String cityCode;
	private String cityName;
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
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
