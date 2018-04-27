package plfx.api.client.api.v1.gj.response;

import plfx.api.client.api.v1.gj.dto.GJJPOrderDTO;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：创建订单结果
 * @类修改者：
 * @修改日期：2015-7-7下午3:06:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:06:21
 */
@SuppressWarnings("serial")
public class CreateJPOrderGJResponse extends ApiResponse {

	/**
	 * 结果代码：航班舱位组合和对应政策token过期或不存在
	 */
	public final static String RESULT_POLICY_NOT_EXIST = "-1";
	/**
	 * 结果代码：航班舱位组合token过期或不存在
	 */
	public final static String RESULT_FLIGHT_NOT_EXIST = "-2";
	/**
	 * 结果代码：经销商的订单ID已存在
	 */
	public final static String RESULT_DEALER_ORDER_ID_EXIST = "-3";
	
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
