package hsl.pojo.dto.hotel;
import hsl.pojo.dto.hotel.base.BasePrepayRuleDTO;

import java.io.Serializable;
public class PrepayRuleDTO extends BasePrepayRuleDTO implements Serializable {
	private static final long serialVersionUID = -5070814556019943342L;
	//预付规则id
	protected int prepayRuleId;

	public int getPrepayRuleId() {
		return prepayRuleId;
	}

	public void setPrepayRuleId(int value) {
		this.prepayRuleId = value;
	}

}
