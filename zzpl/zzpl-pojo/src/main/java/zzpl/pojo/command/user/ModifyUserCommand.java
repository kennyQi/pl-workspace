package zzpl.pojo.command.user;

import java.util.Date;

public class ModifyUserCommand {

	/**
	 * 状态 0:不能替别人买票 1:可以替别人买票
	 */
	private String[] buyOthers;

	private String userID;
	/**
	 * 用户编号
	 */
	private String userNO;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 身份证
	 */
	private String idCardNO;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 头像
	 */
	private String image;
	/**
	 * 头像
	 */
	private String imageID;
	/**
	 * 省份
	 */
	private String provinceID;
	/**
	 * 地区
	 */
	private String cityID;
	/**
	 * 联系电话
	 */
	private String linkMobile;
	/**
	 * 联系邮箱
	 */
	private String linkEmail;
	/**
	 * 部门ID
	 */
	private String departmentID;
	/**
	 * 角色
	 */
	private String[] roleID;

	public String[] getBuyOthers() {
		return buyOthers;
	}

	public void setBuyOthers(String[] buyOthers) {
		this.buyOthers = buyOthers;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public String getProvinceID() {
		return provinceID;
	}

	public void setProvinceID(String provinceID) {
		this.provinceID = provinceID;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String[] getRoleID() {
		return roleID;
	}

	public void setRoleID(String[] roleID) {
		this.roleID = roleID;
	}

}
