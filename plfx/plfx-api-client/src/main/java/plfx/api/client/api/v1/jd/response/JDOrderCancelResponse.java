package plfx.api.client.api.v1.jd.response;

import plfx.api.client.base.slfx.ApiResponse;

/****
 * 
 * @类功能说明：取消酒店订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年5月27日下午1:23:44
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDOrderCancelResponse extends ApiResponse {

	/**
	 * 订单id
	 */
	private int orderId;
	/***
	 * 取消类型
	 */
	private String cancelCode;
	/**
	 * 取消具体原因
	 */
	private String reason;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCancelCode() {
		return cancelCode;
	}

	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
