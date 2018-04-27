package hg.demo.member.common.domain.model;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.common.base.BaseStringIdModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 
* <p>Title: UserBaseInfo</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 上午11:55:01
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX + "BASEINFO")
public class UserBaseInfo extends BaseStringIdModel {

	/**
	 * 
	 */
	@Column(name = "USER_NAME", length = 256)
	private String userName;

	/**
	 * 
	 */
	@Column(name = "EMAIL", length = 128)
	private String email;
	
	/**
	 * 
	 */
	@Column(name = "SEX", length = 8)
	private Integer sex;
	
	/**
	 * 
	 */
	@Column(name = "AGE", length = 8)
	private Integer age;
	
	/**
	 * 
	 */
	@Column(name = "ADDRESS", length = 1024)
	private String address;
	
	@Column(name = "PHONE", length = 32)
	private String phone;
	
	@Column(name = "PASSWORD", length = 128)
	private String password;
	
	/**
	 * 用户状态： 1激活，0未激活，-1冻结
	 */
	@Column(name = "STATUS", length = 8)
	private Integer status;
	
	/**
	 * 来源
	 */
	@Column(name = "REGISTER_APP_ID", length = 128)
	private String registerAppId;
	
	/**
	 * 
	 */
	@Column(name = "CREATE_TIME")
	private Timestamp createTime;
	
	/**
	 * 
	 */
	@Column(name = "UPDATE_TIME")
	private Timestamp updateTime;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getRegisterAppId() {
		return registerAppId;
	}

	public void setRegisterAppId(String registerAppId) {
		this.registerAppId = registerAppId;
	}

}
