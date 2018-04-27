package lxs.api.v1.response.user;

import java.util.List;

import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.ContactsDTO;

public class QueryContactsResponse extends ApiResponse {
	private List<ContactsDTO> contactsList;

	public List<ContactsDTO> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<ContactsDTO> contactsList) {
		this.contactsList = contactsList;
	}
}
