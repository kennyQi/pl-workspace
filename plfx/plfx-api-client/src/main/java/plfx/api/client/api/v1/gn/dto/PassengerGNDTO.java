package plfx.api.client.api.v1.gn.dto;

import plfx.api.client.common.BaseDTO;

@SuppressWarnings("serial")
public class PassengerGNDTO extends BaseDTO{
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
	
}
