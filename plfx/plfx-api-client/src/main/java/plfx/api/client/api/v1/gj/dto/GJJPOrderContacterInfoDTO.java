package plfx.api.client.api.v1.gj.dto;

/**
 * @类功能说明：联系人信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:02:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:02:25
 */
public class GJJPOrderContacterInfoDTO {

	/**
	 * 联系人姓名
	 */
	private String contactName;

	/**
	 * 联系人电话
	 */
	private String contactMobile;

	/**
	 * 联系人邮件地址
	 */
	private String contactMail;

	/**
	 * 联系地址
	 */
	private String contactAddress;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

}
