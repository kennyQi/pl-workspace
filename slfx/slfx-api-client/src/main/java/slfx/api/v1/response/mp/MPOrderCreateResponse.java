package slfx.api.v1.response.mp;

import slfx.api.base.ApiResponse;

/**
 * 门票下单返回
 * @author yuxx
 *
 */
@SuppressWarnings("serial")
public class MPOrderCreateResponse extends ApiResponse {

	/**
	 * 商城订单号
	 */
	private String orderId;

	public final static Integer MP_ORDER_FAIL_POLICY_OVERTIME = -1;	//	政策过期
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
