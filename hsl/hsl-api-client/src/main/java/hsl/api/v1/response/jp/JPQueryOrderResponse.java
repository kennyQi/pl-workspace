package hsl.api.v1.response.jp;

import java.util.List;

import hsl.api.base.ApiResponse;
import hsl.pojo.dto.jp.JPOrderDTO;

/**
 * 查询机票订单响应
 * 
 * @author yuxx
 * 
 */
public class JPQueryOrderResponse extends ApiResponse {

	/**
	 * 机票订单
	 */
	private List<JPOrderDTO> jpOrders;
	
	/**
	 * 订单总条数
	 */
	private Integer totalCount;

	public List<JPOrderDTO> getJpOrders() {
		return jpOrders;
	}

	public void setJpOrders(List<JPOrderDTO> jpOrders) {
		this.jpOrders = jpOrders;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
