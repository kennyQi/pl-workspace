package plfx.mp.tcclient.tc.dto.base;


public class CountyListByCityIdDto extends BaseDto {	
	/**
	 * \城市ID
	 */
	private Integer cityId;

	public CountyListByCityIdDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.base.request.ParamCountyListByCityId");
		this.setResult("slfx.mp.tcclient.tc.pojo.base.response.ResultCountyListByCityId");
		this.setServiceName("GetCountyListByCityId");
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
}
