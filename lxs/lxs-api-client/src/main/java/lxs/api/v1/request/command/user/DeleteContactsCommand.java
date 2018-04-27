package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class DeleteContactsCommand extends ApiPayload {

	private String contactsID;

	public String getContactsID() {
		return contactsID;
	}

	public void setContactsID(String contactsID) {
		this.contactsID = contactsID;
	}

}
