package plfx.gjjp.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明：联系人信息
 * @类修改者：
 * @修改日期：2015-6-29下午5:02:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-6-29下午5:02:25
 */
@Embeddable
@SuppressWarnings("serial")
public class GJJPOrderContacterInfo implements Serializable {

	/**
	 * 联系人姓名
	 */
	@Column(name = "CONTACT_NAME", length = 64)
	private String contactName;

	/**
	 * 联系人电话
	 */
	@Column(name = "CONTACT_MOBILE", length = 64)
	private String contactMobile;

	/**
	 * 联系人邮件地址
	 */
	@Column(name = "CONTACT_MAIL", length = 128)
	private String contactMail;

	/**
	 * 联系地址
	 */
	@Column(name = "CONTACT_ADDRESS", length = 512)
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
