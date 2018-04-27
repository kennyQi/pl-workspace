package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseDrrRuleDTO;

public class DrrRuleDTO extends BaseDrrRuleDTO {
	private static final long serialVersionUID = -929589443487539158L;
	//促销规则编号
	protected int drrRuleId;

	public int getDrrRuleId() {
		return drrRuleId;
	}

	public void setDrrRuleId(int value) {
		this.drrRuleId = value;
	}

}
