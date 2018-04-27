package hsl.pojo.qo.hotel;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class OrderCancelQO extends BaseQo{
	
  /**
   * 所属订单ID	
   */
  private String orderId;

public String getOrderId() {
	return orderId;
}

public void setOrderId(String orderId) {
	this.orderId = orderId;
}
  
  
}
