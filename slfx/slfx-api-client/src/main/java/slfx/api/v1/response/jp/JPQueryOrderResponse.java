package slfx.api.v1.response.jp;

import java.util.List;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.order.JPOrderDTO;

/**
 * 
 * @类功能说明：查询机票订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:44:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
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
