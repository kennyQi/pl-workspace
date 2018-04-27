package zzpl.api.client.dto.user;

import java.util.Date;
import java.util.List;

import zzpl.api.client.dto.BaseDTO;

@SuppressWarnings("serial")
public class UserDTO extends BaseDTO {

	/**
	 * 用户编号
	 */
	private String userNO;

	/**
	 * 登录名
	 */
	private String loginName;

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
	 * 状态
	 */
	private Integer status;

	/**
	 * 注册时间
	 */
	private Date createTime;

	/**
	 * 用户状态
	 */
	private Integer activated;

	/**
	 * 关联角色信息
	 */
	private List<UserRoleDTO> userRoleDTOs;

	/**
	 * 角色列表
	 */
	private List<String> roleList;

	/**
	 * 1:替别人买票
	 */
	private Integer buyOthers;

	public Integer getBuyOthers() {
		return buyOthers;
	}

	public void setBuyOthers(Integer buyOthers) {
		this.buyOthers = buyOthers;
	}

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<UserRoleDTO> getUserRoleDTOs() {
		return userRoleDTOs;
	}

	public void setUserRoleDTOs(List<UserRoleDTO> userRoleDTOs) {
		this.userRoleDTOs = userRoleDTOs;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

}