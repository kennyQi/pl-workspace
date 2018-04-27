

package hsl.pojo.command.hotel;

import java.io.Serializable;

import slfx.jd.pojo.dto.ylclient.order.OrderCancelCommandDTO;


public class JDOrderCancelCommand implements Serializable{
	private OrderCancelCommandDTO ordercancel;

	public OrderCancelCommandDTO getOrdercancel() {
		return ordercancel;
	}

	public void setOrdercancel(OrderCancelCommandDTO ordercancel) {
		this.ordercancel = ordercancel;
	}
	
}
