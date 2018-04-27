package lxs.api.v1.dto.user;

import lxs.api.v1.dto.BaseDTO;
import lxs.api.v1.dto.ImageDTO;

@SuppressWarnings("serial")
public class UserDTO extends BaseDTO {

	private UserAuthInfoDTO authInfo;

	private UserBaseInfoDTO baseInfo;

	private UserContactInfoDTO contactInfo;

	private ImageDTO titleImage;

	private UserStatusDTO status;

	
	
	

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

}
