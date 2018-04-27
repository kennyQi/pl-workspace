package zzpl.api.client.request.user;

import zzpl.api.client.base.ApiPayload;

@SuppressWarnings("serial")
public class ModifyUserCommand extends ApiPayload {

	private String userID;
	/**
	 * 真实姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 联系电话
	 */
	private String linkMobile;
	/**
	 * 联系邮箱
	 */
	private String linkEmail;
	/**
	 * 头像
	 */
	private String imageInfo;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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

	public String getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(String imageInfo) {
		this.imageInfo = imageInfo;
	}

}
