package plfx.api.client.api.v1.mp.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.pojo.dto.order.MPOrderDTO;

/**
 * 门票订单查询返回
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPQueryOrderResponse extends ApiResponse {

	private List<MPOrderDTO> orders;

	private Integer totalCount;

	public List<MPOrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<MPOrderDTO> orders) {
		this.orders = orders;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
