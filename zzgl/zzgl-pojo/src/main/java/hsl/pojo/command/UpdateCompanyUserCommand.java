package hsl.pojo.command;

import hg.common.component.BaseCommand;

/**
 * 更新企业用户信息
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class UpdateCompanyUserCommand extends BaseCommand{
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 头像
	 */
	private String image;
	/**
	 * 类型
	 */
	private Integer type;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBirthday() {
		return birthday;
	}

}
