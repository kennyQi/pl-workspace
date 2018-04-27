package slfx.api.v1.response.mp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.mp.pojo.dto.order.MPOrderDTO;

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
