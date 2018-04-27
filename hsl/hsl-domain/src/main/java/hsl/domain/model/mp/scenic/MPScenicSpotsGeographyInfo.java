package hsl.domain.model.mp.scenic;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
/**
 * 景点地理信息(省市区经纬度等)
 * 
 * @author Administrator
 */
@Embeddable
public class MPScenicSpotsGeographyInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 景区地址
	 */
	@Column(name = "SCENICSPOT_ADDRESS", length = 512)
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
	 * 经度
	 */
	@Transient
	private Double longitude;
	/**
	 * 纬度
	 */
	@Transient
	private Double latitude;

	@Column(name="TRAFFIC",length=4000)
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

}