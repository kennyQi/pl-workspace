package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

public class GuaranteeRuleDTO extends BaseGuaranteeRuleDTO implements Serializable{


	//担保规则id
	protected int guranteeRuleId;

	public int getGuranteeRuleId() {
		return guranteeRuleId;
	}

	public void setGuranteeRuleId(int value) {
		this.guranteeRuleId = value;
	}

}
