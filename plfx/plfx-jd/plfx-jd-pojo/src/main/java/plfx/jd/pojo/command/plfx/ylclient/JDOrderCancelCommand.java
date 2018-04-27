

package plfx.jd.pojo.command.plfx.ylclient;

import java.io.Serializable;

import plfx.jd.pojo.dto.ylclient.order.OrderCancelCommandDTO;


public class JDOrderCancelCommand implements Serializable{
	private static final long serialVersionUID = 8631269910967782213L;
	
	private OrderCancelCommandDTO ordercancel;

	public OrderCancelCommandDTO getOrdercancel() {
		return ordercancel;
	}

	public void setOrdercancel(OrderCancelCommandDTO ordercancel) {
		this.ordercancel = ordercancel;
	}
	
}
