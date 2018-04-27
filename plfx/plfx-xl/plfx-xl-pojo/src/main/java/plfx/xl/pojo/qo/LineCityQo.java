package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;
/***
 * 
 * @类功能说：该国家所有城市QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日上午9:26:13
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineCityQo extends BaseQo{
	
	/**
	 * 城市名称
	 */
	private String name;

	/***
	 * 城市代码
	 */
	private String code;

	/***
	 * 城市所在的国家代码
	 */
	private String parentCode;

	/***
	 * 城市所在的国家名称
	 */
	private String countryName;

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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	
}
