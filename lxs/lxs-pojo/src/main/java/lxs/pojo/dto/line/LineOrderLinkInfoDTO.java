package lxs.pojo.dto.line;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderLinkInfoDTO extends BaseDTO{
	/**
	 * 联系人姓名
	 */
	private String linkName;
	
	/**
	 * 联系人手机号
	 */
	private String linkMobile;
	
	/**
	 * 联系人邮箱
	 */
	private String email;
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
