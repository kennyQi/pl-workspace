package plfx.mp.tcclient.tc.dto.base;
/**
 * 名称查询区划信息dto
 * @author zhangqy
 *
 */

public class DivisionInfoByNameDto extends BaseDto {
	/**
	 * 区划名称
	 */
	private String divisionName;

	public DivisionInfoByNameDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.base.request.ParamDivisionInfoByName");
		this.setResult("slfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoByName");
		this.setServiceName("GetDivisionInfoByName");
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
}
