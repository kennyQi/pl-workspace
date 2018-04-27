package plfx.jd.pojo.dto.ylclient.hotel;

import plfx.jd.pojo.dto.ylclient.order.BaseGuaranteeRuleDTO;
@SuppressWarnings("serial")
public class GuaranteeRuleDTO extends BaseGuaranteeRuleDTO {
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
