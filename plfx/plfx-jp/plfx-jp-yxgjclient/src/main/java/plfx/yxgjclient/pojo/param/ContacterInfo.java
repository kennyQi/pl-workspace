package plfx.yxgjclient.pojo.param;

/**
 * 联系人信息
 * @author guotx
 * 2015-07-07
 */
public class ContacterInfo {
	/**
	 * 联系人姓名，非必填
	 */
	private String contactName;
	/**
	 * 联系人电话，非必填
	 */
	private String contactPhone;
	/**
	 * 联系人邮件地址，非必填
	 */
	private String contactMailAdd;
	/**
	 * 联系人地址，非必填
	 */
	private String contactAddress;
	/**
	 * 邮寄地址，非必填
	 */
	private String sendToAddress;
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactMailAdd() {
		return contactMailAdd;
	}
	public void setContactMailAdd(String contactMailAdd) {
		this.contactMailAdd = contactMailAdd;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getSendToAddress() {
		return sendToAddress;
	}
	public void setSendToAddress(String sendToAddress) {
		this.sendToAddress = sendToAddress;
	}
}
