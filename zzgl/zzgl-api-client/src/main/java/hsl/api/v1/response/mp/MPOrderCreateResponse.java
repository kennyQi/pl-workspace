package hsl.api.v1.response.mp;

import hsl.api.base.ApiResponse;

/**
 * 门票下单返回
 * @author yuxx
 *
 */
public class MPOrderCreateResponse extends ApiResponse {

	/**
	 * 经销商订单号
	 */
	private String orderId;

	public final static String MP_ORDER_FAIL_POLICY_OVERTIME = "-1";	//	政策过期

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
