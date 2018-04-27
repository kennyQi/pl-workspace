package plfx.api.client.api.v1.mp.response;

import plfx.api.client.base.slfx.ApiResponse;

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
