package plfx.api.client.api.v1.gj.response;

import java.util.List;

import plfx.api.client.api.v1.gj.dto.GJJPOrderDTO;
import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：国际机票订单查询结果
 * @类修改者：
 * @修改日期：2015-7-8下午5:53:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午5:53:24
 */
@SuppressWarnings("serial")
public class JPOrderGJResponse extends ApiResponse {

	/**
	 * 国际机票订单
	 */
	private List<GJJPOrderDTO> orders;

	public List<GJJPOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<GJJPOrderDTO> orders) {
		this.orders = orders;
	}

}
