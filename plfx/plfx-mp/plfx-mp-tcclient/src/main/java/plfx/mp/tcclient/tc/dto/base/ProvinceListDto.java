package plfx.mp.tcclient.tc.dto.base;


public class ProvinceListDto extends BaseDto {
	
	public ProvinceListDto(){
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.base.request.ParamProvinceList");
		this.setResult("slfx.mp.tcclient.tc.pojo.base.response.ResultProvinceList");
		this.setServiceName("GetProvinceList");
	}
}
