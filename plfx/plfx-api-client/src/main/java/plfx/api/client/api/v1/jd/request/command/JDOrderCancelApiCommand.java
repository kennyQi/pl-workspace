

package plfx.api.client.api.v1.jd.request.command;

import plfx.api.client.base.slfx.ApiPayload;
import plfx.jd.pojo.dto.ylclient.order.OrderCancelCommandDTO;

/***
 * 
 * @类功能说明：取消订单Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月24日下午4:30:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDOrderCancelApiCommand extends ApiPayload{
	/***
	 * 取消订单dto
	 */
	private OrderCancelCommandDTO ordercancel;

	public OrderCancelCommandDTO getOrdercancel() {
		return ordercancel;
	}

	public void setOrdercancel(OrderCancelCommandDTO ordercancel) {
		this.ordercancel = ordercancel;
	}
	
}
