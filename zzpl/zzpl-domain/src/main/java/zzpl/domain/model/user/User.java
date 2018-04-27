package zzpl.domain.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "USER")
public class User extends BaseModel {

	/**
	 * 用户编号
	 */
	@Column(name = "USER_NO", length = 64)
	private String userNO;

	/**
	 * 登录名
	 */
	@Column(name = "SHOW_LOGIN_NAME", length = 64)
	private String showLoginName;
	
	/**
	 * 登录名
	 */
	@Column(name = "LOGIN_NAME", length = 64)
	private String loginName;

	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", length = 64)
	private String password;

	/**
	 * 真实姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 身份证号
	 */
	@Column(name = "IDCARD_NO", length = 18)
	private String idCardNO;

	/**
	 * 性别
	 */
	@Column(name = "GENDER", columnDefinition = M.NUM_COLUM)
	private Integer gender;

	/**
	 * 生日
	 */
	@Column(name = "BIRTHDAY", columnDefinition = M.DATE_COLUM)
	private Date birthday;

	/**
	 * 头像
	 */
	@Column(name = "IMAGE", length = 512)
	private String image;

	/**
	 * 该头像在图片服务中的ID
	 */
	@Column(name = "IMAGE_ID", length = 512)
	private String imageID;

	/**
	 * 省份id
	 */
	@Column(name = "PROVINCE_ID", length = 64)
	private String provinceID;

	/**
	 * 城市id
	 */
	@Column(name = "CITY_ID", length = 64)
	private String cityID;

	/**
	 * 公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;

	/**
	 * 公司名称
	 */
	@Column(name = "COMPANY_NAME", length = 512)
	private String companyName;

	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID", length = 512)
	private String departmentID;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME", length = 512)
	private String departmentName;

	/**
	 * 联系电话
	 */
	@Column(name = "LINK_Mobile", length = 512)
	private String linkMobile;

	/**
	 * 联系邮箱
	 */
	@Column(name = "LINK_EMAIL", length = 512)
	private String linkEmail;

	/**
	 * 状态 0:正常 1:删除
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;
	
	/**
	 * 状态 0:不能替别人买票 1:可以替别人买票
	 */
	@Column(name = "BUY_OTHERS", columnDefinition = M.NUM_COLUM)
	private Integer buyOthers;

	/**
	 * 注册时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 用户状态
	 */
	@Column(name = "ACTIVATED", columnDefinition = M.NUM_COLUM)
	private Integer activated;

	/**
	 * 关联角色信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = { CascadeType.ALL })
	private List<UserRole> userRoles;

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public String getShowLoginName() {
		return showLoginName;
	}

	public void setShowLoginName(String showLoginName) {
		this.showLoginName = showLoginName;
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

	public Integer getBuyOthers() {
		return buyOthers;
	}

	public void setBuyOthers(Integer buyOthers) {
		this.buyOthers = buyOthers;
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

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

		
}
