package lxs.pojo.qo.user.contacts;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class ContactsQO extends BaseQo {

	private String userId;

	private String contactsIDCardNO;

	public String getContactsIDCardNO() {
		return contactsIDCardNO;
	}

	public void setContactsIDCardNO(String contactsIDCardNO) {
		this.contactsIDCardNO = contactsIDCardNO;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
