package slfx.jp.pojo.dto.supplier.yg;

import java.util.Map;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * @类功能说明：订单查询返回DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-26下午2:39:40
 * @版本：V1.0
 *
 */
public class YGQueryOrderDTO extends BaseJpDTO{
	
	private static final long serialVersionUID = 8058397562289709749L;

	/**
	 * 接口错误代码，0表示调用成功
	 */
	private String errorCode;
	
	/**
	 * 错误描述信息
	 */
	private String errorMsg;
	
	/**
	 * 订单状态 
	 */
	private String status;
	
	/**
	 * 政策编号
	 */
	private String policyId;
	
	/**
	 * 出票平台
	 */
	private String PlatCode;
	
	/**
	 * 平台订单号
	 */
	private String platOrderNo;
	
	/**
	 * 易购订单号
	 */
	private String orderNo;
	
	/**
	 * 订座记录编号
	 */
	private String pnr; 
	
	/**
	 * 订单金额
	 */
	private Double balanceMoney;
	
	/**
	 * 票款合计
	 */
	private Double fare;
	
	/**
	 * 税款合计
	 */
	private Double taxAmount;
	
	/**
	 * 佣金合计
	 */
	private Double commAmount;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 支付金额
	 */
	private Double payMoney;
	
	/**
	 * 支付账号
	 */
	private String payAccount;
	
	/**
	 * 交易流水
	 */
	private String tradeNo;

	private Map<String,String> ticket;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPlatCode() {
		return PlatCode;
	}

	public void setPlatCode(String platCode) {
		PlatCode = platCode;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Map<String, String> getTicket() {
		return ticket;
	}

	public void setTicket(Map<String, String> ticket) {
		this.ticket = ticket;
	}
	
}
