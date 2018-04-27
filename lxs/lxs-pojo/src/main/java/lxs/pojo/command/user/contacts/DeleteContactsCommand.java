package lxs.pojo.command.user.contacts;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class DeleteContactsCommand extends BaseCommand {
	private String contactsID;

	public String getContactsID() {
		return contactsID;
	}

	public void setContactsID(String contactsID) {
		this.contactsID = contactsID;
	}
}
