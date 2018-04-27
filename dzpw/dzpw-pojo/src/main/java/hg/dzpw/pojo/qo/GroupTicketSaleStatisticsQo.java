package hg.dzpw.pojo.qo;

import hg.dzpw.pojo.common.BasePojoQo;

import java.util.Date;

/**
 * @类功能说明：联票销售统计查询对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:22:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:22:37
 */
public class GroupTicketSaleStatisticsQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/** 查询类型：按销量 */
	public final static int QUERY_TYPE_ORDER_TIKECT_COUNT = 1;
	/** 查询类型：按销售额 */
	public final static int QUERY_TYPE_SALE_TIKECT_AMOUNT = 2;
	

	/**
	 * 查询类型
	 */
	private int queryType = QUERY_TYPE_ORDER_TIKECT_COUNT;

	/**
	 * 门票政策ID(当此ID不为空时忽略名称条件)
	 */
	private String ticketPolicyId;

	/**
	 * 门票政策名称
	 */
	private String ticketPolicyName;
	
	/**
	 * 门票政策类型  单票、联票
	 */
	private Integer ticketPolicyType;

	/**
	 * 销售日期（开始）
	 */
	private Date orderDateBegin;

	/**
	 * 销售日期（截止）
	 */
	private Date orderDateEnd;
	
	/**
	 * 所属景区
	 */
	private String scenicSpotName;
	
	/**
	 * 经销商名称
	 */
	private String dealerName;
	
	/**
	 * 订单状态
	 */
	private Integer status;

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
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

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
