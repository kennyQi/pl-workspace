package plfx.mp.tcclient.tc.dto.base;

/**
 * 按区划ID查询区划信息
 * @author zhangqy
 *
 */
public class DivisionInfoByIdDto extends BaseDto {
	/**
	 * 区划Id
	 */
	private Integer divisionId;

	public DivisionInfoByIdDto() {
		super();
		this.setParam("slfx.mp.tcclient.tc.pojo.base.request.ParamDivisionInfoById");
		this.setResult("slfx.mp.tcclient.tc.pojo.base.response.ResultDivisionInfoById");
		this.setServiceName("GetDivisionInfoById");
	}

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}
	
	
}
