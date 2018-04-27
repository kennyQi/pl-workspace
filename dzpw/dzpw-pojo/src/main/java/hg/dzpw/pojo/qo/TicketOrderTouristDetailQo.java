package hg.dzpw.pojo.qo;

import java.util.Date;

import hg.dzpw.pojo.common.BasePojoQo;

/**
 * @类功能说明：门票订单里的用户查询对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:35:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:35:24
 */
public class TicketOrderTouristDetailQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票政策ID（联票销售统计明细）
	 */
	private String ticketPolicyId;

	/**
	 * 经销商ID（经销商销售统计明细）
	 */
	private String dealerId;

	/**
	 * 销售日期（开始）
	 */
	private Date orderDateBegin;

	/**
	 * 销售日期（截止）
	 */
	private Date orderDateEnd;

	/**
	 * 订单编号
	 */
	private String orderNo;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDateBegin() {
		return orderDateBegin;
	}

	public void setOrderDateBegin(Date orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}

	public Date getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

}
