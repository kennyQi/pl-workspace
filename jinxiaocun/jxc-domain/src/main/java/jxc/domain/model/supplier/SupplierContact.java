package jxc.domain.model.supplier;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 供应商联系方式
 * @author liujz
 *
 */
@Embeddable
public class SupplierContact {
	
	/**
	 * 联系电话
	 */
	@Column(name="SUPPLIER_PHONE",length=15)
	private String phone;
	
	/**
	 * 联系地址
	 */
	@Column(name="SUPPLIER_ADDRESS",length=100)
	private String address;
	
	/**
	 * 邮编
	 */
	@Column(name="SUPPLIER_POSTCODE",length=6)
	private String postCode;
	
	/**
	 * 联系邮箱
	 */
	@Column(name="SUPPLIER_EMAIL",length=100)
	private String email;
	
	/**
	 * 传真号
	 */
	@Column(name="SUPPLIER_FAX",length=20)
	private String fax;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
}
