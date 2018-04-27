package lxs.pojo.command.user.contacts;

import hg.common.component.BaseCommand;
import lxs.pojo.dto.user.contacts.ContactsDTO;


@SuppressWarnings("serial")
public class CreateContactsCommand extends BaseCommand{
	private ContactsDTO contactsDTO;

	public ContactsDTO getContactsDTO() {
		return contactsDTO;
	}

	public void setContactsDTO(ContactsDTO contactsDTO) {
		this.contactsDTO = contactsDTO;
	}
}
