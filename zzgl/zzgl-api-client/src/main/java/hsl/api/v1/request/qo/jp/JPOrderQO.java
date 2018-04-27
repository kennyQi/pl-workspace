package hsl.api.v1.request.qo.jp;

import hsl.api.base.ApiPayload;

/**
 * 订单查询
 */
@SuppressWarnings("serial")
public class JPOrderQO extends ApiPayload {
	
	/**
	 * 按用户id查询
	 */
	private String userId;
	
	/**
	 * 按订单号查询
	 */
	private String dealerOrderCode;
	
	/**
	 * 显示全部订单明细
	 */
	private Boolean detail;
	
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

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

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

	public Boolean getDetail() {
		return detail;
	}

	public void setDetail(Boolean detail) {
		this.detail = detail;
	}
	
	
}
