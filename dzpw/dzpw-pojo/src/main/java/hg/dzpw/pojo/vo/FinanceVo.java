package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：经销商端结算明细视图对象
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-1下午4:59:44
 * @版本：
 */
public class FinanceVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupTicketId;
	
	private String singleTicketId;
	
	/**
	 * 经销商手续费
	 */
	private Double dealerSettlementFee;
	/**
	 * 所属单位
	 */
	private String scenicSpotNameStr;
	/**
	 * 产品名称
	 */
	private String policyName;
	/**
	 * 票务编号
	 */
	private String ticketNo;
	/**
	 * 门票政策类型 单票、联票
	 */
	private Integer ticketPolicyType;
	/**
	 * 游客姓名
	 */
	private String touristName;
	/**
	 * 游客证件号码
	 */
	private String tourisIdNo;
	
	/**
	 * 经销商名称
	 */
	private String dealerName;
	
	/**
	 * 经销商结算账户类型
	 */
	private Integer dealerAccountType;
	
	/**
	 * 经销商结算账户
	 */
	private String dealerAccountNumber;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 结算日期
	 */
	private Date settlementDate;
	/**
	 * 经销商结算价
	 */
	private Double dealerAmount;
	
	/**
	 * 景区结算价
	 */
	private Double settlementPrice;
	/**
	 * 景区手续费
	 */
	private Double settlementFee;
	/**
	 * 总计结算价
	 */
	private Double totalAmount;
	/**
	 * 总计手续费
	 */
	private Double totalFee;
	/**
	 * 总计票价
	 */
	private Double totalPrice;
	
	/**
	 * 产品编号
	 */
	private String policyNo;
	
	/**
	 * 下单时间
	 */
	private Date createDate;
	
	/**
	 * 出票时间
	 */
	private Date ticketDate;
	
	/**
	 * 支付流水号
	 */
	private String payTradeNo;
	
	/**
	 * 退款账号
	 */
	private String refundAccount;
	
	/**
	 * 退款流水号
	 */
	private String refundBatchNo;
	
	/**
	 * 总计退款金额
	 */
	private Double refundAmmount;
	
	/**
	 * 退款时间
	 */
	private Date refundTime;

	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 景区状态
	 */
	private Integer scenicStatus;
	
	/**
	 * 预定人
	 */
	private String linkMan;
	
	/**
	 * 结算时间
	 */
	private Date settleDate;
	
	/**
	 * 有效期结束时间
	 */
	private Date useDateEnd;
	
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Integer getTicketPolicyType() {
		return ticketPolicyType;
	}

	public void setTicketPolicyType(Integer ticketPolicyType) {
		this.ticketPolicyType = ticketPolicyType;
	}

	public String getTouristName() {
		return touristName;
	}

	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}

	public String getTourisIdNo() {
		return tourisIdNo;
	}

	public void setTourisIdNo(String tourisIdNo) {
		this.tourisIdNo = tourisIdNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getDealerAmount() {
		return dealerAmount;
	}

	public void setDealerAmount(Double dealerAmount) {
		this.dealerAmount = dealerAmount;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getRefundAccount() {
		return refundAccount;
	}

	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	public String getRefundBatchNo() {
		return refundBatchNo;
	}

	public void setRefundTradeNo(String refundBatchNo) {
		this.refundBatchNo = refundBatchNo;
	}

	public Double getRefundAmmount() {
		return refundAmmount;
	}

	public void setRefundAmmount(Double refundAmmount) {
		this.refundAmmount = refundAmmount;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getScenicSpotNameStr() {
		return scenicSpotNameStr;
	}

	public void setScenicSpotNameStr(String scenicSpotNameStr) {
		this.scenicSpotNameStr = scenicSpotNameStr;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Double getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public Integer getScenicStatus() {
		return scenicStatus;
	}

	public void setScenicStatus(Integer scenicStatus) {
		this.scenicStatus = scenicStatus;
	}

	public String getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}
	
	//运营端财务明细
	/**
	 * 经销商总结算
	 */
	private Double totalDealerAmount;
	
	/**
	 * 景区总结算
	 */
	private Double totalScenicAmount;
	
	/**
	 * 经销商总手续费
	 */
	private Double totalDealerFee;
	
	/**
	 * 景区总手续费
	 */
	private Double totalScenicFee;
	
	/**
	 * 平台收益
	 */
	private Double platformIncome;

	public Double getTotalDealerAmount() {
		return totalDealerAmount;
	}

	public void setTotalDealerAmount(Double totalDealerAmount) {
		this.totalDealerAmount = totalDealerAmount;
	}

	public Double getTotalScenicAmount() {
		return totalScenicAmount;
	}

	public void setTotalScenicAmount(Double totalScenicAmount) {
		this.totalScenicAmount = totalScenicAmount;
	}

	public Double getTotalDealerFee() {
		return totalDealerFee;
	}

	public void setTotalDealerFee(Double totalDealerFee) {
		this.totalDealerFee = totalDealerFee;
	}

	public Double getTotalScenicFee() {
		return totalScenicFee;
	}

	public void setTotalScenicFee(Double totalScenicFee) {
		this.totalScenicFee = totalScenicFee;
	}

	public Double getPlatformIncome() {
		return platformIncome;
	}

	public void setPlatformIncome(Double platformIncome) {
		this.platformIncome = platformIncome;
	}

	public Double getDealerSettlementFee() {
		return dealerSettlementFee;
	}

	public void setDealerSettlementFee(Double dealerSettlementFee) {
		this.dealerSettlementFee = dealerSettlementFee;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Date getUseDateEnd() {
		return useDateEnd;
	}

	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
	}

	public String getDealerAccountNumber() {
		return dealerAccountNumber;
	}

	public void setDealerAccountNumber(String dealerAccountNumber) {
		this.dealerAccountNumber = dealerAccountNumber;
	}

	public Integer getDealerAccountType() {
		return dealerAccountType;
	}

	public void setDealerAccountType(Integer dealerAccountType) {
		this.dealerAccountType = dealerAccountType;
	}
	
}
