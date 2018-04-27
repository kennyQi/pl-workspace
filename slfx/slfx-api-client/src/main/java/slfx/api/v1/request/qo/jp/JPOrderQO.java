package slfx.api.v1.request.qo.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：订单查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:46:08
 * @版本：V1.0
 *
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
	private String orderId;
	
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
