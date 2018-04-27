package lxs.pojo.command.user.contacts;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class ModifyContactsCommand extends BaseCommand {

	private String contactsID;

	private String contactsName;

	private String contactsIDCardNO;

	private String mobile;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContactsID() {
		return contactsID;
	}

	public void setContactsID(String contactsID) {
		this.contactsID = contactsID;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsIDCardNO() {
		return contactsIDCardNO;
	}

	public void setContactsIDCardNO(String contactsIDCardNO) {
		this.contactsIDCardNO = contactsIDCardNO;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
