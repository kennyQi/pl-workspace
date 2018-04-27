package zzpl.pojo.dto.jp.plfx.gj;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;


@SuppressWarnings("serial")
public class GJPassengerBaseInfoDTO extends BaseDTO {
	/**
	 * 乘客姓名
	 */
	private String passengerName;

	/**
	 * 乘客类型
	 * 
	 * @see GJ#PASSENGER_TYPE_MAP
	 */
	private Integer passengerType;

	/**
	 * 乘客性别
	 * 
	 * 1-男0-女
	 */
	private Integer sex;

	/**
	 * 出生年月
	 */
	private Date birthday;

	/**
	 * 国籍二字码
	 */
	private String nationality;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 证件类型
	 * 
	 * @see GJ#IDTYPE_MAP
	 */
	private Integer idType;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 证件有效期
	 */
	private Date expiryDate;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(Integer passengerType) {
		this.passengerType = passengerType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
