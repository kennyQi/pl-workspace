package slfx.jp.command.client;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：易购旅客信息commmand
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:08:06
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGPassengerCommand extends BaseCommand {

	/**
	 * 旅客姓名,如:  ：必填
	 * 中文(张三) 
	 * 英文(Jesse/W)
	 */
	private String name;
	
	/**
	 * 旅客类型: ：必填
	 * [ADT] 成人
	 * [CHD] 儿童
	 * [INF] 婴儿
	 * [UM]  无陪伴儿童
	 */
	private String psgType;
	
	/**
	 * 证件类型  ：必填
	 */
	private String identityType;
	
	/**
	 * 航信系统证件类型 : ：必填
	 * [NI] 身份证号
	 * [FOID] 护照号
	 * [ID] 其它类型
	 */
	private String cardType;
	
	/**
	 * 证件号  ：必填
	 */
	private String cardNo;
	
	/**
	 * 移动电话
	 */
	private String mobilePhone;
	
	/**
	 * 保险份数 ：必填
	 */
	private Integer insueSum;
	
	/**
	 * 票面价（单人报价） ：必填
	 */
	private Double fare;
	
	/**
	 * 销售价   ：必填
	 */  
	private Double salePrice;
	
	/**
	 * 税款合计  ：必填
	 */
	private Double taxAmount;

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
	
}
