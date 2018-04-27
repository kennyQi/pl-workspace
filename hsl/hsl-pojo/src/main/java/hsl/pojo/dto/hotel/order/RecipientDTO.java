package hsl.pojo.dto.hotel.order;

import java.io.Serializable;

/***
 * 
 * @类功能说明：收件人
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月20日下午4:43:55
 * @版本：V1.0
 * 
 */
public class RecipientDTO implements Serializable {
	private static final long serialVersionUID = 6992215035301617109L;
	/**
	 * 省
	 */
	protected String province;
	/**
	 * 市
	 */
	protected String city;
	/**
	 * 区域
	 */
	protected String district;
	/**
	 * 街道
	 */
	protected String street;
	/**
	 * 邮编
	 */
	protected String postalCode;
	/**
	 * 姓名
	 */
	protected String name;
	/**
	 * 电话
	 */
	protected String phone;
	/**
	 * email
	 */
	protected String email;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
