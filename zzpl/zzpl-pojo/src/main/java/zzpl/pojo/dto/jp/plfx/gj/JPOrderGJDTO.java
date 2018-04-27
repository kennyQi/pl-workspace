package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class JPOrderGJDTO extends BaseDTO{
	/**
	 * 国际机票订单
	 */
	private List<GJJPOrderDTO> orders;

	public List<GJJPOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<GJJPOrderDTO> orders) {
		this.orders = orders;
	}

}