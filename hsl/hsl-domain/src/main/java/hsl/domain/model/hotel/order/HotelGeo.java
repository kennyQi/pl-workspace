package hsl.domain.model.hotel.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.HotelGeoCommand;
import hsl.pojo.command.line.ad.CreateLineIndexAdCommand;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Entity
@Table(name = M.TABLE_PREFIX_HSL_JD+ "HOTELGEO")
public class HotelGeo extends BaseModel{
	/**
	 * 国家
	 */
	@Column(name = "COUNTRY", length = 20)
	private String country;
	/**
	 * 省份名称
	 */
	@Column(name = "PROVINCENAME", length = 20)
	private String provinceName;
	/**
	 * 省份id
	 */
	@Column(name = "PROVINCEID", length = 20)
	private String provinceId;
	/**
	 * 城市名称
	 */
	@Column(name = "CITYNAME", length = 20)
	private String cityName;
	/**
	 * 城市编码
	 */
	@Column(name = "CITYCODE", length = 20)
	private String cityCode;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "hotelGeo",cascade={CascadeType.ALL})
	private List<District> districts;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "hotelGeo",cascade={CascadeType.ALL})
	private List<CommericalLocation> commericalLocations;
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
	public List<District> getDistricts() {
		return districts;
	}
	public void setDistricts(List<District> districts) {
		this.districts = districts;
	}
	public List<CommericalLocation> getCommericalLocations() {
		return commericalLocations;
	}
	public void setCommericalLocations(List<CommericalLocation> commericalLocations) {
		this.commericalLocations = commericalLocations;
	}
	public void createHotelGeo(HotelGeoCommand command){
		this.setId(UUIDGenerator.getUUID());
		this.setCityCode(command.getCityCode());
		this.setCityName(command.getCityName());
		this.setCountry(command.getCountry());
		this.setProvinceId(command.getProvinceId());
		this.setProvinceName(command.getProvinceName());
	}
}
