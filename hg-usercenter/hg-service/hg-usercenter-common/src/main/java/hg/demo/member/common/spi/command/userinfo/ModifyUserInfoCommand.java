package hg.demo.member.common.spi.command.userinfo;

import hg.framework.common.base.BaseSPICommand;

/**
 * 
* <p>Title: SaveUserInfoCommand</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月28日 上午9:11:18
 */
@SuppressWarnings("serial")
public class ModifyUserInfoCommand extends BaseSPICommand {

	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 注册应用ID
	 */
	private String appId;
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 
	 */
	private String email;
	
	/**
	 * 
	 */
	private Integer sex = 0;
	
	/**
	 * 
	 */
	private Integer age;
	
	/**
	 * 
	 */
	private String address;
	
	private String phone;
	
	/**
	 * 用户状态： 1激活，0未激活，-1冻结
	 */
	private Integer status = 1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
