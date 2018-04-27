package jxc.emsclient.ems.dto.stockOut;

import java.io.Serializable;

/**
 * 收货人信息
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class OrderReceiveInfoDTO implements Serializable{
	
	/**
	 * 收货人姓名
	 */
	private String consignee;
	
	/**
	 * 收货人所在省
	 */
	private String prov;
	
	/**
	 * 收货人所在市
	 */
	private String city;
	
	/**
	 * 收货人所在县（区），若没有则不填
	 */
	private String district;
	
	/**
	 * 收货人详细地址
	 */
	private String address;
	
	/**
	 * 收货人手机号
	 */
	private String mobile;
	
	/**
	 * 收货人电话
	 */
	private String phone;
	
	
	/**
	 * 收货方编码，适用于公司客户、调入仓库编码、供应商编码（暂不使用）
	 */
	private String receiver_code;
	
	/**
	 * 收货方名称（暂不使用）
	 */
	private String receiver_name;
	
	/**
	 * 收货人邮编（暂不使用）
	 */
	private String post_Code;

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	//不用的字段
	public String getReceiver_code() {
		return receiver_code;
	}

	public void setReceiver_code(String receiver_code) {
		this.receiver_code = receiver_code;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getPost_Code() {
		return post_Code;
	}

	public void setPost_Code(String post_Code) {
		this.post_Code = post_Code;
	}
	
	
	
}
