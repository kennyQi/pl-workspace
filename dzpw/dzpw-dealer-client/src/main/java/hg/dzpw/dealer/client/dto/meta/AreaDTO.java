package hg.dzpw.dealer.client.dto.meta;

import hg.dzpw.dealer.client.common.BaseDTO;

/**
 * @类功能说明：地区
 * @类修改者：
 * @修改日期：2014-11-24上午9:54:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-24上午9:54:43
 */
@SuppressWarnings("serial")
public class AreaDTO extends BaseDTO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 市代码
	 */
	private String parentCode;

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

}
