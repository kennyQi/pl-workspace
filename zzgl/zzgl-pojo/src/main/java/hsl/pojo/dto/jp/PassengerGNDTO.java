package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class PassengerGNDTO extends BaseDTO{
	/**
	 * 票号
	 */
	private String airId; 
	/***
	 *乘客姓名
	 */
	private String passengerName;
	/***
	 * 乘客类型
	 */
	private String passengerType;
	/**
	 * 证件类型
	 */
	private String idType;
	/***
	 * 证件号
	 */
	private String idNo;
	/**
	 * 手机号
	 */
	private String mobile;
	
	
	public String getAirId() {
		return airId;
	}
	public void setAirId(String airId) {
		this.airId = airId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
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
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
