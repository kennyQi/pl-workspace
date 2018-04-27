package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseGuaranteeRuleDTO;

public class GuaranteeRuleDTO extends BaseGuaranteeRuleDTO {
	private static final long serialVersionUID = 9035551149812382804L;
	/**
	 * 担保规则Id
	 */
	protected int guranteeRuleId;

	public int getGuranteeRuleId() {
		return guranteeRuleId;
	}

	public void setGuranteeRuleId(int value) {
		this.guranteeRuleId = value;
	}

}
