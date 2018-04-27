package plfx.api.client.api.v1.gj.dto;

import java.util.Date;

import plfx.api.client.common.PlfxApiConstants.GJ;

/**
 * @类功能说明：乘客信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:03:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:03:39
 */
public class GJPassengerBaseInfoDTO {

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
	
	/**
	 * 旅客居住地址
	 */
	private String resiAddr;
	/**
	 * 旅客居住邮编
	 */
	private String resiPost;
	/**
	 * 旅客目的地址
	 */
	private String dstAddr;
	/**
	 * 旅客目的邮编
	 */
	private String dstPost;

	public String getResiAddr() {
		return resiAddr;
	}

	public void setResiAddr(String resiAddr) {
		this.resiAddr = resiAddr;
	}

	public String getResiPost() {
		return resiPost;
	}

	public void setResiPost(String resiPost) {
		this.resiPost = resiPost;
	}

	public String getDstAddr() {
		return dstAddr;
	}

	public void setDstAddr(String dstAddr) {
		this.dstAddr = dstAddr;
	}

	public String getDstPost() {
		return dstPost;
	}

	public void setDstPost(String dstPost) {
		this.dstPost = dstPost;
	}

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
