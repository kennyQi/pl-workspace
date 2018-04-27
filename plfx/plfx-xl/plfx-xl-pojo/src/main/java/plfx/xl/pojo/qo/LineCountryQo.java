package plfx.xl.pojo.qo;

import hg.common.component.BaseQo;

/****
 * 
 * @类功能说明：国家QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日上午9:27:13
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineCountryQo extends BaseQo{
	
	
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
