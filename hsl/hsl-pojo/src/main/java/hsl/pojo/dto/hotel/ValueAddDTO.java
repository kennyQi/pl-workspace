package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseValueAddRuleDTO;

public class ValueAddDTO extends BaseValueAddRuleDTO {
	private static final long serialVersionUID = 1L;
	/**
	 *  增值服务编号
	 */
	protected String valueAddId;

	public String getValueAddId() {
		return valueAddId;
	}

	public void setValueAddId(String value) {
		this.valueAddId = value;
	}

}
