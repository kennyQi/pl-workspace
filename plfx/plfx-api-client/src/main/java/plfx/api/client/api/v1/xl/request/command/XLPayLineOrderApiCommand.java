package plfx.api.client.api.v1.xl.request.command;

import plfx.api.client.base.slfx.ApiPayload;

/**
 * 
 * @类功能说明：接口支付线路订单Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午6:07:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLPayLineOrderApiCommand extends ApiPayload {
	
	/**
	 * 线路订单编号
	 */
	private String lineOrderID;
	
	/**
	 * 支付的游玩人编号，用,隔开
	 */
	private String lineOrderTravelers;
	
	/**
	 * 支付方式
	 * 1:支付宝
	 */
	private Integer paymentType;
	
	/**
	 * 支付账号
	 */
	private String paymentAccount;
	
	/**
	 * 付款姓名
	 */
	private String paymentName;
	
	/**
	 * 流水号
	 */
	private String serialNumber;
	
	/**
	 * 支付金额
	 */
	private Double paymentAmount;
	
	/**
	 * 付款类型：1:定金；2：尾款；3全款
	 */
	private Integer shopPayType;
	
	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public String getLineOrderTravelers() {
		return lineOrderTravelers;
	}

	public void setLineOrderTravelers(String lineOrderTravelers) {
		this.lineOrderTravelers = lineOrderTravelers;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Integer getShopPayType() {
		return shopPayType;
	}

	public void setShopPayType(Integer shopPayType) {
		this.shopPayType = shopPayType;
	}

	
}
