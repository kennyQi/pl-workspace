package plfx.api.client.api.v1.jd.response;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;

/**
 * 
 * @类功能说明：创建酒店订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:42:42
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDOrderCreateResponse extends ApiResponse {

	private OrderCreateResultDTO orderCreateResultDTO;

	public OrderCreateResultDTO getOrderCreateResultDTO() {
		return orderCreateResultDTO;
	}

	public void setOrderCreateResultDTO(OrderCreateResultDTO orderCreateResultDTO) {
		this.orderCreateResultDTO = orderCreateResultDTO;
	}
}
