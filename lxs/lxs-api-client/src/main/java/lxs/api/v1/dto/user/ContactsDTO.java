package lxs.api.v1.dto.user;

import lxs.api.v1.dto.BaseDTO;

@SuppressWarnings("serial")
public class ContactsDTO extends BaseDTO {
	/**
	 * 属于哪个用户
	 */
	private String user;
	/**
	 * 联系人姓名
	 */
	private String contactsName;
	/**
	 * 身份证号
	 */
	private String ContactsIdCardNO;

	/**
	 * 电话
	 */
	private String mobile;
	
	/**
	 * 游客类型
	 */
	private String type;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 支付状态
	 */
	private String payStatus;
	
	/*public final static Integer TYPE_ADULT = adult;
	public final static Integer TYPE_CHILD = child;*/

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getContactsIdCardNO() {
		return ContactsIdCardNO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setContactsIdCardNO(String contactsIdCardNO) {
		ContactsIdCardNO = contactsIdCardNO;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
