package slfx.mp.domain.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.mp.domain.model.M;

import hg.common.component.BaseModel;

/**
 * 下单人信息
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "ORDER_USER")
public class OrderUserInfo extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道用户id
	 */
	@Column(name="CHANNEL_USER_ID", length = 64)
	private String channelUserId;
	/**
	 * 身份证号
	 */
	@Column(name="ID_CARD_NO", length = 64)
	private String idCardNo;
	/**
	 * 姓名
	 */
	@Column(name="NAME", length = 64)
	private String name;
	/**
	 * 手机
	 */
	@Column(name="MOBILE", length = 64)
	private String mobile;
	/**
	 * 地址
	 */
	@Column(name="ADDRESS", length = 512)
	private String address;
	/**
	 * 邮编
	 */
	@Column(name="POSTCODE", length = 16)
	private String postcode;
	/**
	 * 邮箱
	 */
	@Column(name="EMAIL", length = 256)
	private String email;

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

}