package zzpl.pojo.dto.jp.plfx.gj;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class GJJPOrderContacterInfoDTO extends BaseDTO{
	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系人电话
	 */
	private String contactMobile;

	/**
	 * 联系人邮件地址
	 */
	private String contactMail;

	/**
	 * 联系地址
	 */
	private String contactAddress;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}
