package slfx.jp.pojo.dto.flight;

import slfx.jp.pojo.dto.order.JPOrderUserInfoDTO;

/**
 * 
 * @类功能说明：乘客信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:53:32
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class FlightPassengerDTO extends JPOrderUserInfoDTO {

	/** 乘客序号 */
	private Integer psgNo;

	/** 乘客类型
		ADT 成人 
		CHD 儿童 
		INF 婴儿 
		UM 无陪伴儿童 
	 */
	private String passangerType;
	/**
	 * 乘客姓名如：中文(张三) 英文(Jesse/W)
	 */
	private String name;
	
	/**
	 * 国籍  汉字
	private String country;
	 */
	
	/**
	 * 乘客证件类型
	 */
	private String identityType;

	/** 航信接口证件类型 */
	private String cardType;

	/**
	 * 证件号   cardType的证件号
	 */
	private String cardNo;

	/** 出生日期 */
	private String birthday;

	/** 跟随成人序号 */
	private Integer carrierPsgNo;
	

	/** 保险份数 */
	private Integer insueSum;

	/** 保险单价 */
	private Double insueFee;
	
	/**
	 * 该乘机人所支付的政策
	 */
	private SlfxFlightPolicyDTO flightPolicyDTO;
	
	/**
	 * 该乘机人所出的机票
	 */
	private TicketDTO ticketDto;
	
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
	 * 乘客手机号
	private String mobile;
	 */
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}	

	public Integer getPsgNo() {
		return psgNo;
	}

	public void setPsgNo(Integer psgNo) {
		this.psgNo = psgNo;
	}

	public String getPassangerType() {
		return passangerType;
	}

	public void setPassangerType(String passangerType) {
		this.passangerType = passangerType;
	}

	public String getCardType() {
		return cardType;
	}
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getCarrierPsgNo() {
		return carrierPsgNo;
	}

	public void setCarrierPsgNo(Integer carrierPsgNo) {
		this.carrierPsgNo = carrierPsgNo;
	}

	public Integer getInsueSum() {
		return insueSum;
	}

	public void setInsueSum(Integer insueSum) {
		this.insueSum = insueSum;
	}

	public Double getInsueFee() {
		return insueFee;
	}

	public void setInsueFee(Double insueFee) {
		this.insueFee = insueFee;
	}

	public SlfxFlightPolicyDTO getFlightPolicyDTO() {
		return flightPolicyDTO;
	}

	public void setFlightPolicyDTO(SlfxFlightPolicyDTO flightPolicyDTO) {
		this.flightPolicyDTO = flightPolicyDTO;
	}

	public TicketDTO getTicketDto() {
		return ticketDto;
	}

	public void setTicketDto(TicketDTO ticketDto) {
		this.ticketDto = ticketDto;
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

	/*public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}*/

}
