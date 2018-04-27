package slfx.api.v1.response.jp;

import java.util.Date;

import slfx.api.base.ApiResponse;

/**
 * 
 * @类功能说明：机票下单结果RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:44:04
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPCreateOrderResponse extends ApiResponse {
	
	/**
	 * 下单成功后返回平台订单号
	 */
	private String orderCode;
	
	/**
	 * PNR
	 */
	private String pnr;
	
	/**
	 * 订单支付总金额
	 */
	private Double payAmount;


	/**
	 * 单人票面价
	 */
	private Double ticketPrice;
	
	/**
	 * 单张机票税款（基建+燃油）
	 */
	private Double singleTaxAmount;
	
	/**
	 * 下单成功时间
	 */
	private Date createDate;
	
	/**
	 * 下单状态
	 */
	private Integer status;
	
	/**
	 * 废票截至时间
	 */
	private Date wastWorkTime;
	
	/**
	 * 出票工作时间
	 */
	private String workTime;
	/**
	 * 退票时间
	 */
	private String refundWorkTime;
	
	/**  出票平台code  供应商的代号如：（001或BT）、（002或B5） 对应agencyName */
	private String platCode;
	
	/**
	 * 商家信息
	 */
	private String agencyName;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Double getSingleTaxAmount() {
		return singleTaxAmount;
	}

	public void setSingleTaxAmount(Double singleTaxAmount) {
		this.singleTaxAmount = singleTaxAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(Date wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundWorkTime() {
		return refundWorkTime;
	}

	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
}
