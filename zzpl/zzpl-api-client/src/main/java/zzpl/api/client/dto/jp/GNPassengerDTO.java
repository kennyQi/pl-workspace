package zzpl.api.client.dto.jp;


public class GNPassengerDTO{
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
	private String idNO;
	/**
	 * 电话
	 */
	private String telephone;
	
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
	public String getIdNO() {
		return idNO;
	}
	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
