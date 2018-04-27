package slfx.mp.tcclient.tc.dto.base;

/**
 * 城市
 * @author zhangqy
 *
 */
public class CityListByProvinceIdDto extends BaseDto {
	/**
	 * 省ID
	 */
	private Integer provinceId;

	public CityListByProvinceIdDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.base.request.ParamCityListByProvinceId");
		this.setResult("slfx.mp.tcclient.tc.pojo.base.response.ResultCityListByProvinceId");
		this.setServiceName("GetCityListByProvinceId");
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
}
