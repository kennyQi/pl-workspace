package lxs.pojo.dto.user.contacts;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class ContactsDTO extends BaseDTO {
	/**
	 * 属于哪个用户
	 */
	private String user;
	/**
	 * 联系人姓名
	 */
	private String contactsName;
	/**
	 * 身份证号
	 */
	private String ContactsIdCardNO;

	public String getContactsIdCardNO() {
		return ContactsIdCardNO;
	}

	public void setContactsIdCardNO(String contactsIdCardNO) {
		ContactsIdCardNO = contactsIdCardNO;
	}

	/**
	 * 电话
	 */
	private String mobile;

	/**
	 * 游客类型
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
