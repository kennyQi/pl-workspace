package plfx.gnjp.domain.model.order;

import hg.common.component.BaseModel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import plfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：乘机人信息 model
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午3:43:40
 * @版本：V1.0
 *
 */
@Entity
@Table(name = J.TABLE_PREFIX_GN + "PASSENGER")
public class GNJPPassenger extends BaseModel {

	private static final long serialVersionUID = 123456789987665L;

	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 旅客类型
	 * 1-成人，2-儿童
	 */
	@Column(name = "PASSENGER_TYPE", length = 8)
	private String psgType;
	
	/**
	 * 成人订单号 orderId
	 */
	@Column(name = "PASSENGER_ADTORDERID", length = 8)
	private String adtOrderId;
	
	/**
	 * 证件类型
	 * 0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证
	 */
	@Column(name = "PASSENGER_IDTYPE", length = 8)
	private String idType;

	/**
	 * 证件号码
	 */
	@Column(name = "PASSENGER_IdNo", length = 64)
	private String idNo;

	/**
	 * 移动电话
	 * 乘客手机号码 必填！！！ 不同乘客手机号码不能一样
	 */
	@Column(name = "PASSENGER_TELEPHONE", length = 64)
	private String telephone;

	/**
	 * 票面价（单人报价）
	 */
	@Column(name = "FARE", columnDefinition = J.MONEY_COLUM)
	private Double fare;
	
	/**
	 * 平台供给商城销售价
	 */
	@Column(name = "SALE_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double salePrice;

	/**
	 * 单人税款合计（基建+燃油）
	 */
	@Column(name = "TAX_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double taxAmount;

	/**
	 * 出生日期 yyyy-MM-dd 成人 儿童 都需要
	 */
	@Column(name = "BIRTH_DAY", length = 16)
	private String birthDay;

	/**
	 * 所出机票号
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FLIGHT_TICKET_ID")
	private GNFlightTicket ticket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JP_ORDER_ID")
	private GNJPOrder jpOrder;

	@Column(name = "JP_CANCEL_ORDER_ID")
	private String jpCancelOrderId;
	
	/**
	 * 拒绝退票理由
	 */
	@Column(name = "JP_REFUSEMEMO", length = 100)
	private String refuseMemo;

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

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public GNFlightTicket getTicket() {
		return ticket;
	}

	public void setTicket(GNFlightTicket ticket) {
		this.ticket = ticket;
	}

	public GNJPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(GNJPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

	public String getJpCancelOrderId() {
		return jpCancelOrderId;
	}

	public void setJpCancelOrderId(String jpCancelOrderId) {
		this.jpCancelOrderId = jpCancelOrderId;
	}

	public String getAdtOrderId() {
		return adtOrderId;
	}

	public void setAdtOrderId(String adtOrderId) {
		this.adtOrderId = adtOrderId;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}
}
