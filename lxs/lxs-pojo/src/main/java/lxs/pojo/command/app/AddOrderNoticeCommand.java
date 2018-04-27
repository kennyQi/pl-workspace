package lxs.pojo.command.app;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class AddOrderNoticeCommand extends BaseCommand {
	private String contactPerson;
	private String phonoNumber;
	private String id;

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhonoNumber() {
		return phonoNumber;
	}

	public void setPhonoNumber(String phonoNumber) {
		this.phonoNumber = phonoNumber;
	}
}
