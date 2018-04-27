package plfx.api.client.api.v1.xl.response;

import java.util.List;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.xl.pojo.dto.order.LineOrderDTO;

/**
 * 
 * @类功能说明：api接口查询线路订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:44:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLQueryLineOrderResponse extends ApiResponse {


	/**
	 * 线路订单list
	 */
	private List<LineOrderDTO> lineOrderList;
	
	/**
	 * 线路总条数
	 */
	private Integer totalCount;

	public List<LineOrderDTO> getLineOrderList() {
		return lineOrderList;
	}

	public void setLineOrderList(List<LineOrderDTO> lineOrderList) {
		this.lineOrderList = lineOrderList;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}
