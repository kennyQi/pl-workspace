package slfx.api.v1.response.jp;

import slfx.api.base.ApiResponse;

/**
 * 
 * @类功能说明：取消订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:43:28
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPCancelOrderTicketResponse extends ApiResponse{
	/**
	 * 订单状态
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
