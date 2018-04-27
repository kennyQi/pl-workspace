package slfx.jp.pojo.dto.order;

import java.io.Serializable;


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
@SuppressWarnings("serial")
public class Passenger  implements Serializable{


	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */
	private String name;

	/**
	 * 旅客类型: [ADT] 成人 [CHD] 儿童 [INF] 婴儿 [UM] 无陪伴儿童
	 */
	private String psgType;

	/**
	 * 证件类型 与cardType设同样的值
	 */
	private String identityType;

	/**
	 * 航信系统证件类型 : [NI] 身份证号 [FOID] 护照号 [ID] 其它类型
	 */
	private String cardType;

	/**
	 * 证件号
	 */
	private String cardNo;

	/**
	 * 移动电话
	 * 乘客手机号码 必填！！！ 不同乘客手机号码不能一样
	 */
	private String mobilePhone;

	/**
	 * 保险份数 没有则传0 不用设
	 */
	private Integer insueSum;

	/**
	 * 票面价（单人报价）
	 */
	private Double fare;
	
	/**
	 * 平台供给商城销售价
	 */
	private Double salePrice;

	/**
	 * 税款合计
	 */
	private Double taxAmount;

	/**
	 * 乘客序号 婴儿的乘客序号为0
	 */
	private Integer psgId;

	/**
	 * 出生日期 yyyy-MM-dd 成人 儿童 婴儿都需要
	 */
	private String birthDay;

	/**
	 * 国籍 汉字
	 */
	private String country;

	/**
	 * 保险单价 不用设
	 */
	private Double insueFee;

	/**
	 * 所出机票
	 */
	private FlightTicket ticket;
	

	private String jpOrderId;
	
	public String getName() {
		return name;
	}

	public String getJpOrderId() {
		return jpOrderId;
	}

	public void setJpOrderId(String jpOrderId) {
		this.jpOrderId = jpOrderId;
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

}
