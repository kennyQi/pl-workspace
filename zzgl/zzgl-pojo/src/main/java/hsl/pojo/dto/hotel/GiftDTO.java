package hsl.pojo.dto.hotel;

import hsl.pojo.dto.hotel.base.BaseGiftRuleDTO;

public class GiftDTO extends BaseGiftRuleDTO {
	private static final long serialVersionUID = 1L;
	//送礼编号id
	protected int giftId;

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int value) {
		this.giftId = value;
	}

}
