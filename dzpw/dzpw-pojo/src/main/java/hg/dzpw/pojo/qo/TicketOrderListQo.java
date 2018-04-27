package hg.dzpw.pojo.qo;

import hg.dzpw.pojo.common.BasePojoQo;

import java.util.Date;

/**
 * @类功能说明：门票列表视图查询对象
 * @类修改者：
 * @修改日期：2015-2-28下午4:05:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-28下午4:05:39
 */
public class TicketOrderListQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID（商户端必须带）
	 */
	private String scenicspotId;

	/**
	 * 门票政策名称（模糊匹配）
	 */
	private String ticketPolicyName;

	/**
	 * 门票政策类型
	 */
	private Integer ticketPolicyType;

	/**
	 * 经销商ID
	 */
	private String fromDealerId;

	/**
	 * 下单时间开始
	 */
	private Date orderDateBegin;

	/**
	 * 下单时间截止
	 */
	private Date orderDateEnd;

	/**
	 * 游客名称（模糊匹配）
	 */
	private String touristName;

	/**
	 * 订单编号（模糊匹配）
	 */
	private String orderId;

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderId;

	/**
	 * 订单状态
	 */
	private Integer orderStatus;

	public String getScenicspotId() {
		return scenicspotId;
	}

	public void setScenicspotId(String scenicspotId) {
		this.scenicspotId = scenicspotId;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getFromDealerId() {
		return fromDealerId;
	}

	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
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

	public String getTouristName() {
		return touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
