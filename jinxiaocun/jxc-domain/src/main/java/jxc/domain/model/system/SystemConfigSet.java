package jxc.domain.model.system;

public class SystemConfigSet {
	public static final String KEY_PURCHASER_INFO_LINKMAN_NAME = "0_linkman_name";
	public static final String KEY_PURCHASER_INFO_PHONE = "1_phone";
	public static final String KEY_PURCHASER_INFO_FAX = "2_fax";
	public static final String KEY_PURCHASER_INFO_CONTACT_ADDRESS = "3_contact_address";
	
	private String linkmanName;
	private String phone;
	private String fax;
	private String contactAddress;

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}
