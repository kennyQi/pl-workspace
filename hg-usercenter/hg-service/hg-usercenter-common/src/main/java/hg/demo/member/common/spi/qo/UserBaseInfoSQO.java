package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 
* <p>Title: UserBaseInfoSQO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午4:54:13
 */
@SuppressWarnings("serial")
public class UserBaseInfoSQO extends BaseSPIQO {

	private String id;
	
	private String userName;

	/**
	 * 
	 */
	private String email;
	
	/**
	 * 
	 */
	private String phone;
	
	/**
	 * 
	 */
	private String address;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
