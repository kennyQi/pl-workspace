package slfx.jp.domain.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import slfx.jp.domain.model.J;
import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：乘机人信息 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午4:28:37
 * @版本：V1.0
 *
 */
@Entity
@Table(name = J.TABLE_PREFIX + "PASSENGER")
public class Passenger extends BaseModel {

	private static final long serialVersionUID = 123456789987665L;

	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 旅客类型: [ADT] 成人 [CHD] 儿童 [INF] 婴儿 [UM] 无陪伴儿童
	 */
	@Column(name = "PASSENGER_TYPE", length = 8)
	private String psgType;

	/**
	 * 证件类型 与cardType设同样的值
	 */
	@Column(name = "IDENTITY_TYPE", length = 8)
	private String identityType;

	/**
	 * 航信系统证件类型 : [NI] 身份证号 [FOID] 护照号 [ID] 其它类型
	 */
	@Column(name = "CARD_TYPE", length = 8)
	private String cardType;

	/**
	 * 证件号
	 */
	@Column(name = "CARD_NO", length = 64)
	private String cardNo;

	/**
	 * 移动电话
	 * 乘客手机号码 必填！！！ 不同乘客手机号码不能一样
	 */
	@Column(name = "MOBILE_PHONE", length = 64)
	private String mobilePhone;

	/**
	 * 保险份数 没有则传0 不用设
	 */
	@Column(name = "INSUE_SUM", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer insueSum;

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
	 * 乘客序号 婴儿的乘客序号为0
	 */
	@Column(name = "PSG_ID", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer psgId;

	/**
	 * 出生日期 yyyy-MM-dd 成人 儿童 婴儿都需要
	 */
	@Column(name = "BIRTH_DAY", length = 16)
	private String birthDay;

	/**
	 * 国籍 汉字
	 */
	@Column(name = "COUNTRY", length = 32)
	private String country;

	/**
	 * 保险单价 不用设
	 */
	@Column(name = "INSUE_FEE", columnDefinition = J.MONEY_COLUM)
	private Double insueFee;

	/**
	 * 所出机票
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FLIGHT_TICKET_ID")
	private FlightTicket ticket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JP_ORDER_ID")
	private JPOrder jpOrder;

	@Column(name = "JP_CANCEL_ORDER_ID")
	private String jpCancelOrderId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsgType() {
		return psgType;
	}

	
	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public void setPsgType(String psgType) {
		this.psgType = psgType;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getInsueSum() {
		return insueSum;
	}

	public void setInsueSum(Integer insueSum) {
		this.insueSum = insueSum;
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

	public Integer getPsgId() {
		return psgId;
	}

	public void setPsgId(Integer psgId) {
		this.psgId = psgId;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getInsueFee() {
		return insueFee;
	}

	public void setInsueFee(Double insueFee) {
		this.insueFee = insueFee;
	}

	public FlightTicket getTicket() {
		return ticket;
	}

	public void setTicket(FlightTicket ticket) {
		this.ticket = ticket;
	}

	public String getJpCancelOrderId() {
		return jpCancelOrderId;
	}

	public void setJpCancelOrderId(String jpCancelOrderId) {
		this.jpCancelOrderId = jpCancelOrderId;
	}

	public JPOrder getJpOrder() {
		return super.getProperty(jpOrder, JPOrder.class);
	}

	public void setJpOrder(JPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

}
