package hsl.pojo.dto.hotel.order;

import hsl.pojo.util.enumConstants.EnumGender;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CustomerDTO implements Serializable{
	/**
	 * 姓名
	 */
    protected String name;
    /**
     * email
     */
    protected String email;
    /**
     * 手机
     */
    protected String mobile;
    /**
     * 电话
     */
    protected String phone;
    /**
     * 传真
     */
    protected String fax;
    /**
     * 性别
     */
    protected EnumGender gender;
    /**
     * 国籍
     */
    protected String nationality;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public EnumGender getGender() {
		return gender;
	}
	public void setGender(EnumGender gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
    
}
