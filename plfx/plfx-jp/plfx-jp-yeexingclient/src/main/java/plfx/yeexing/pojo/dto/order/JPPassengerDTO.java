package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

@SuppressWarnings("serial")
public class JPPassengerDTO extends BaseJpDTO{

	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */

	private String name;

	/**
	 * 旅客类型
	 * 1-成人，2-儿童
	 */

	private String psgType;
	
	/**
	 * 成人订单号 orderId
	 */

	private String adtOrderId;
	
	/**
	 * 证件类型
	 * 0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证
	 */

	private String idType;

	/**
	 * 证件号码
	 */

	private String idNo;

	/**
	 * 移动电话
	 * 乘客手机号码 必填！！！ 不同乘客手机号码不能一样
	 */
	
	private String telephone;

	/**
	 * 票面价（单人报价）
	 */

	private Double fare;
	
	/**
	 * 平台供给商城销售价
	 */

	private Double salePrice;

	/**
	 * 单人税款合计（基建+燃油）
	 */

	private Double taxAmount;


    /***
     * 取消订单id
     */
	private String jpCancelOrderId;
	
	private String refuseMemo;
	
	/***
	 * 票号信息
	 */
	private JPFlightTicketDTO ticket;
	
	public JPFlightTicketDTO getTicket() {
		return ticket;
	}

	public void setTicket(JPFlightTicketDTO ticket) {
		this.ticket = ticket;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsgType() {
		return psgType;
	}

	public void setPsgType(String psgType) {
		this.psgType = psgType;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}


	public String getAdtOrderId() {
		return adtOrderId;
	}

	public void setAdtOrderId(String adtOrderId) {
		this.adtOrderId = adtOrderId;
	}

	public String getJpCancelOrderId() {
		return jpCancelOrderId;
	}

	public void setJpCancelOrderId(String jpCancelOrderId) {
		this.jpCancelOrderId = jpCancelOrderId;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	
}
