package lxs.api.v1.request.command.user;

import lxs.api.base.ApiPayload;
import lxs.api.v1.dto.user.ContactsDTO;

@SuppressWarnings("serial")
public class CreateContactsCommand extends ApiPayload {
	private ContactsDTO contactsDTO;

	public ContactsDTO getContactsDTO() {
		return contactsDTO;
	}

	public void setContactsDTO(ContactsDTO contactsDTO) {
		this.contactsDTO = contactsDTO;
	}
}
