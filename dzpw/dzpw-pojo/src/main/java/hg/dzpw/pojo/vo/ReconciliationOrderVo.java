package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：支付对账单视图对象
 * @类修改者：
 * @修改日期：2014-11-14上午10:21:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14上午10:21:24
 */
public class ReconciliationOrderVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单ID
	 */
	private String ticketOrderId;
	/**
	 * 门票ID
	 */
	private String groupTicketId;
	/**
	 * 门票票号
	 */
	private String ticketNo;
	/**
	 * 门票政策名称
	 */
	private String policyName;
	/**
	 * 下单日期
	 */
	private Date orderDate;
	/**
	 * 支付日期
	 */
	private Date payDate;
	/**
	 * 应收金额
	 */
	private Number price;
	/**
	 * 实收金额
	 */
	private Number paidAmount;
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	/**
	 * 景区名称
	 */
	private String scenicSpotName;
	/**
	 * 景区结算金额
	 */
	private Number settlementAmount;
	/**
	 * 初次入园时间
	 */
	private Date firstTimeUseDate;

	public String getTicketOrderId() {
		return ticketOrderId;
	}

	public void setTicketOrderId(String ticketOrderId) {
		this.ticketOrderId = ticketOrderId;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Number getPrice() {
		if (price == null)
			return 0;
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public Number getPaidAmount() {
		if (paidAmount == null)
			return 0;
		return paidAmount;
	}

	public void setPaidAmount(Number paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Number getSettlementAmount() {
		if (settlementAmount == null)
			return 0;
		return settlementAmount;
	}

	public void setSettlementAmount(Number settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getFirstTimeUseDate() {
		return firstTimeUseDate;
	}

	public void setFirstTimeUseDate(Date firstTimeUseDate) {
		this.firstTimeUseDate = firstTimeUseDate;
	}

}
