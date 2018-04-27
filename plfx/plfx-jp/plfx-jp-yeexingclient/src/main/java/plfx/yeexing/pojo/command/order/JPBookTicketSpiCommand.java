package plfx.yeexing.pojo.command.order;

import hg.common.component.BaseCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerInfoDTO;



/****
 * 
 * @类功能说明：下单COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:07:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPBookTicketSpiCommand extends BaseCommand {

	/***
	 * 合作伙伴用户名
	 */
	private String userName;
	
	/**
	 * 加密字符串             来自于QueryAirpolicy的结果
	 */
	private String encryptString;

	/***
	 * 成人订单号          订儿童票时必填，且需先订成人票
	 */
	private String orderId;
	/***
	 * 外部订单号    
	 */
	private String outOrderId;
	
	/***
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	/***
	 * 乘客信息对象 
	 */
	private YeeXingPassengerInfoDTO yeeXingpassengerInfoDTO;
	/***
	 * 签名  所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	/***
	 * 密钥
	 */
	private String key;
	
	
	/***
	 * 乘客信息xml
	 */
	private String passengerInfo;
	
	/**
	 * 来自经销商的ID
	 * 
	 */
	private String fromDealerId;
	
	/****
	 * 舱位代码
	 */
	private String cabinCode;
	
	/***
	 * 舱位名称
	 */
	private String cabinName;
	
	//-----------查缓存需要用到------------------------------
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 起飞日期 格式：2012-05-10
	 */
	private String startDate;
	
	/**
	 * 用户支付总价
	 */
	private Double userPayAmount;
	
	/**
	 * 用户支付现金
	 */
	private Double userPayCash;

	public Double getUserPayCash() {
		return userPayCash;
	}
	public void setUserPayCash(Double userPayCash) {
		this.userPayCash = userPayCash;
	}
	
	public Double getUserPayAmount() {
		return userPayAmount;
	}
	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}
	public String getCabinCode() {
		return cabinCode;
	}
	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}
	public String getCabinName() {
		return cabinName;
	}
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}
	public JPBookTicketSpiCommand() {
		
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}


	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}


	public String getFromDealerId() {
		return fromDealerId;
	}

	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
	}

	public JPBookTicketSpiCommand(YeeXingPassengerInfoDTO passengerInfoDTO) {
			StringBuilder sb=new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>");
			sb.append("<input>");
			sb.append("<passengers>");
			for(YeeXingPassengerDTO p:passengerInfoDTO.getPassengerList()){
			    sb.append("<passenger Name=\""+p.getPassengerName()+"\" Type=\""+p.getPassengerType()+"\" IdType=\""+p.getIdType()+"\" IdNo=\""+p.getIdNo()+"\"/>");
			}
		    sb.append("<passengers>");
		    sb.append("<contacter Name=\""+passengerInfoDTO.getName()+"\" Telephone=\""+passengerInfoDTO.getTelephone()+"\"/>");
		    sb.append("<input>");
		    this.passengerInfo=sb.toString();
	}
	

	 
	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public YeeXingPassengerInfoDTO getYeeXingpassengerInfoDTO() {
		return yeeXingpassengerInfoDTO;
	}
	public void setYeeXingpassengerInfoDTO(
			YeeXingPassengerInfoDTO yeeXingpassengerInfoDTO) {
		this.yeeXingpassengerInfoDTO = yeeXingpassengerInfoDTO;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPassengerInfo() {
		return passengerInfo;
	}

	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
