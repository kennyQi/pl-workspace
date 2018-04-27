package hg.dzpw.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：结算明细视图对象
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-1下午4:59:44
 * @版本：
 */
public class SettleDetailVo implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 结算日期
	 */
	private Date settlementDate;
	/**
	 * 景区结算价
	 */
	private Double scenicSpotAmount;
	/**
	 * 手续费
	 */
	private Double amountFee;
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
	 * 出票时间
	 */
	private Date payDate;
	
	/**
	 * 景区状态
	 */
	private Integer scenicSpotStatus;
	

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

	public Double getScenicSpotAmount() {
		return scenicSpotAmount;
	}

	public void setScenicSpotAmount(Double scenicSpotAmount) {
		this.scenicSpotAmount = scenicSpotAmount;
	}

	public Double getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(Double amountFee) {
		this.amountFee = amountFee;
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

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Integer getScenicSpotStatus() {
		return scenicSpotStatus;
	}

	public void setScenicSpotStatus(Integer scenicSpotStatus) {
		this.scenicSpotStatus = scenicSpotStatus;
	}

}
