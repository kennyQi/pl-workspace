package plfx.api.client.api.v1.mp.request.qo;

import plfx.api.client.base.slfx.ApiPayload;

/**
 * 门票订单查询
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPOrderQO extends ApiPayload {

	/**
	 * 按用户id查询
	 */
	private String userId;

	/**
	 * 按订单号查询
	 */
	private String orderId;

	/**
	 * 显示全部订单明细
	 */
//	private Boolean detail;

	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 返回条数
	 */
	private Integer pageSize;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

//	public Boolean getDetail() {
//		return detail;
//	}
//
//	public void setDetail(Boolean detail) {
//		this.detail = detail;
//	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
