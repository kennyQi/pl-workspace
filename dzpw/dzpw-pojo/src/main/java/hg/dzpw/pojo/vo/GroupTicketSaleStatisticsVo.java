package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @类功能说明：门票销售统计视图对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:07:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:07:06
 */
public class GroupTicketSaleStatisticsVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 门票政策ID
	 */
	private String ticketPolicyId;

	/**
	 * 排名
	 */
	private int rank;

	/**
	 * 门票政策名称
	 */
	private String ticketPolicyName;

	/**
	 * 平均结算价
	 */
	private Double settlementAvgAmount;
	
	/**
	 * 结算价 1.3版本 TicketPolicy.baseInfo.price值
	 */
	private Double settlementAmount;
	
	/**
	 * 销量合计
	 */
	private int saleTicketTotalCount;

	/**
	 * 日均销量
	 */
	private double saleTicketDayCount;

	/**
	 * 销售额
	 */
	private double saleTotalAmount;
	
	/**
	 * 门票政策类型  单票、联票
	 */
	private Integer ticketPolicyType;
	/**
	 * 票务编号
	 */
	private String ticketNo;
	/**
	 * 销售（结算）价格
	 */
	private Double settleAmount;
	/**
	 * 订单编号
	 */
	private String orderId;
	
	/**
	 * 经销商名称
	 */
	private String dealerName;
	/**
	 * 销售时间
	 */
	private Date createOrderDate;
	/**
	 * 景区名称
	 */
	private String scenicSpotName;
	/**
	 * 产品名称
	 */
	private String policyName;
	
	/**
	 * 订单状态
	 */
	private Integer status;

	public String getTicketPolicyId() {
		return ticketPolicyId;
	}

	public void setTicketPolicyId(String ticketPolicyId) {
		this.ticketPolicyId = ticketPolicyId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getTicketPolicyName() {
		return ticketPolicyName;
	}

	public void setTicketPolicyName(String ticketPolicyName) {
		this.ticketPolicyName = ticketPolicyName;
	}

	public double getSettlementAvgAmount() {
		return settlementAvgAmount;
	}

	public void setSettlementAvgAmount(double settlementAvgAmount) {
		this.settlementAvgAmount = settlementAvgAmount;
	}

	public int getSaleTicketTotalCount() {
		return saleTicketTotalCount;
	}

	public void setSaleTicketTotalCount(Long saleTicketTotalCount) {
		if (saleTicketTotalCount != null)
			this.saleTicketTotalCount = saleTicketTotalCount.intValue();
		else
			this.saleTicketTotalCount = 0;
	}

	public double getSaleTicketDayCount() {
		return saleTicketDayCount;
	}

	public void setSaleTicketDayCount(double saleTicketDayCount) {
		this.saleTicketDayCount = saleTicketDayCount;
	}

	public double getSaleTotalAmount() {
		return saleTotalAmount;
	}

	public void setSaleTotalAmount(double saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Date getCreateOrderDate() {
		return createOrderDate;
	}

	public void setCreateOrderDate(Date createOrderDate) {
		this.createOrderDate = createOrderDate;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Double getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
