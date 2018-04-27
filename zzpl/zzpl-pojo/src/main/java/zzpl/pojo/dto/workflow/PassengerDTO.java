package zzpl.pojo.dto.workflow;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class PassengerDTO extends BaseDTO {
	/***
	 *乘客姓名
	 */
	private String passengerName;
	
	/***
	 * 乘客类型
	 */
	private String passengerType;

	/**
	 * 国籍二字码
	 */
	private String nationality;
	
	/**
	 * 出生年月
	 */
	private Date birthday;
	
	/**
	 * 乘客性别
	 * 
	 * 1-男0-女
	 */
	private Integer sex;
	
	/**
	 * 证件类型
	 */
	private String idType;
	
	/***
	 * 证件号
	 */
	private String idNO;
	
	/**
	 * 证件有效期
	 */
	private Date expiryDate;
	
	/**
	 * 电话
	 */
	private String telephone;
	
	/**
	 * 票号
	 */
	private String airID;

	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 订单类型 
	 * 1：国内机票
	 * 2：国际机票
	 */
	private String orderType;
	
	/**
	 * ---------------------------------取消订单信息----------------------------------
	 * 申请种类 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	private String refundType;

	/***
	 * 申请理由
	 */
	private String refundMemo;

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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
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

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
	
}
