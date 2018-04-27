package lxs.pojo.dto.user.user;

import lxs.pojo.BaseDTO;
import lxs.pojo.dto.user.ImageDTO;

@SuppressWarnings("serial")
public class UserDTO extends BaseDTO {

	/**
	 * 用户信息
	 */
	private UserAuthInfoDTO authInfo;

	/**
	 * 基本信息
	 */
	private UserBaseInfoDTO baseInfo;

	/**
	 * 联系信息
	 */
	private UserContactInfoDTO contactInfo;

	/**
	 * 用户图片信息
	 */
	private ImageDTO titleImage;

	/**
	 * 状态信息
	 */
	private UserStatusDTO status;
	
	/**
	 * 交易信息
	 */
	private UserBusinessInfoDTO businessInfo; 

	public UserAuthInfoDTO getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(UserAuthInfoDTO authInfo) {
		this.authInfo = authInfo;
	}

	public UserBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public UserContactInfoDTO getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(UserContactInfoDTO contactInfo) {
		this.contactInfo = contactInfo;
	}

	public UserStatusDTO getStatus() {
		return status;
	}

	public void setStatus(UserStatusDTO status) {
		this.status = status;
	}

	public ImageDTO getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(ImageDTO titleImage) {
		this.titleImage = titleImage;
	}

	public UserBusinessInfoDTO getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(UserBusinessInfoDTO businessInfo) {
		this.businessInfo = businessInfo;
	}
	
}
