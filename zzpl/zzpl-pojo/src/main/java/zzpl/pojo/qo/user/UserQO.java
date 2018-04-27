package zzpl.pojo.qo.user;

import java.util.Date;

import hg.common.component.BaseQo;
import hg.common.util.Md5Util;

@SuppressWarnings("serial")
public class UserQO extends BaseQo {

	/**
	 * ID
	 */
	private String id;

	/**
	 * roleID
	 */
	private String roleID;

	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 登录名
	 */
	private String showLoginName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String name;

	/**
	 * 身份证号
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
	 * 省份id
	 */
	private String provinceID;

	/**
	 * 城市id
	 */
	private String cityID;

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 部门ID
	 */
	private String departmentID;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 联系电话
	 */
	private String linkMobile;

	/**
	 * 联系邮箱
	 */
	private String linkEmail;

	/**
	 * 注册时间
	 */
	private Date createTime;

	/**
	 * 用户状态
	 */
	private Integer activated;
	/**
	 * 用户编号
	 */
	private String userNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleID() {
		return roleID;
	}
	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getShowLoginName() {
		return showLoginName;
	}
	public void setShowLoginName(String showLoginName) {
		this.showLoginName = showLoginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getActivated() {
		return activated;
	}
	public void setActivated(Integer activated) {
		this.activated = activated;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public static void main(String[] args) {
		System.out.println(Md5Util.MD5("admin"));
		System.out.println(Md5Util.MD5(Md5Util.MD5("123456")));
	}
	
}
