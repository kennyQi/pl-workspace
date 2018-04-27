package plfx.mp.pojo.dto.order;

import plfx.mp.pojo.dto.BaseMpDTO;

/**
 * 门票下单人信息
 * 
 * @author Administrator
 */
public class MPOrderUserInfoDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道用户id
	 */
	private String channelUserId;
	/**
	 * 身份证号
	 */
	private String idCardNo;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postcode;
	/**
	 * 邮箱
	 */
	private String email;

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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

}