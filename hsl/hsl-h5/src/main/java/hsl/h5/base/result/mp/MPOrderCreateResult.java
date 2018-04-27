package hsl.h5.base.result.mp;
import hsl.h5.base.result.api.ApiResult;
/**
 * 门票下单返回
 * @author yuxx
 *
 */
public class MPOrderCreateResult extends ApiResult {

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
