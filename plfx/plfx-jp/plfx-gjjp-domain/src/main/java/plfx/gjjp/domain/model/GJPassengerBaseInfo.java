package plfx.gjjp.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.domain.model.J;

/**
 * @类功能说明：乘客基本信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:03:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:03:39
 */
@Embeddable
@SuppressWarnings("serial")
public class GJPassengerBaseInfo implements Serializable {

	/**
	 * 乘客姓名
	 */
	@Column(name = "PASSENGER_NAME", length = 64)
	private String passengerName;

	/**
	 * 乘客类型
	 * 
	 * @see GJJPConstants#PASSENGER_TYPE_MAP
	 */
	@Column(name = "PASSENGER_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer passengerType;

	/**
	 * 乘客性别
	 * 
	 * 1-男0-女
	 */
	@Column(name = "SEX", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer sex;

	/**
	 * 出生年月
	 */
	@Column(name = "BIRTHDAY", columnDefinition = J.DATE_COLUM)
	private Date birthday;

	/**
	 * 国籍二字码
	 */
	@Column(name = "NATIONALITY", length = 16)
	private String nationality;

	/**
	 * 手机号码
	 */
	@Column(name = "MOBILE", length = 32)
	private String mobile;

	/**
	 * 证件类型
	 * 
	 * @see GJJPConstants#IDTYPE_MAP
	 */
	@Column(name = "ID_TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer idType;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;

	/**
	 * 证件有效期
	 */
	@Column(name = "EXPIRY_DATE", columnDefinition = J.DATE_COLUM)
	private Date expiryDate;

	/**
	 * 旅客居住地址
	 */
	@Column(name = "RESI_ADDR", length = 100)
	private String resiAddr;
	/**
	 * 旅客居住邮编
	 */
	@Column(name = "RESI_POST", length = 100)
	private String resiPost;
	/**
	 * 旅客目的地址
	 */
	@Column(name = "DST_ADDR", length = 100)
	private String dstAddr;

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

	/**
	 * 旅客目的邮编
	 */
	@Column(name = "DST_POST", length = 100)
	private String dstPost;

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
