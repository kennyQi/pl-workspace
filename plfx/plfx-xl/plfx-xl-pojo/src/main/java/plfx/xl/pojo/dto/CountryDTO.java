package plfx.xl.pojo.dto;

/****
 * 
 * @类功能说明：国家DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日上午10:31:07
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CountryDTO extends BaseXlDTO{
	/**
	 * 国家名称
	 */
	private String name;
	
	/**
	 * 国家代码
	 */
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
