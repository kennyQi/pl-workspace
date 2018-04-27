package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CreateJPOrderGJDTO extends BaseDTO{
	/**
	 * 国际机票订单
	 */
	private GJJPOrderDTO order;

	public GJJPOrderDTO getOrder() {
		return order;
	}

	public void setOrder(GJJPOrderDTO order) {
		this.order = order;
	}

}
