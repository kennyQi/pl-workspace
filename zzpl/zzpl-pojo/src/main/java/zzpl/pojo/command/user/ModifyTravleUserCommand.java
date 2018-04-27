package zzpl.pojo.command.user;

import hg.common.component.BaseCommand;

import java.util.Date;

@SuppressWarnings("serial")
public class ModifyTravleUserCommand extends BaseCommand {

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
	private String imageID;
	/**
	 * 联系电话
	 */
	private String linkMobile;
	/**
	 * 联系邮箱
	 */
	private String linkEmail;
	/**
	 * 公司ID
	 */
	private String companyID;
	
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
	public String getImageID() {
		return imageID;
	}
	public void setImageID(String imageID) {
		this.imageID = imageID;
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
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
}
