package hg.pojo.dto.supplier;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SupplierLinkManDTO implements Serializable{
	
	/**
	 * 供应商联系人id
	 */
	private String supplierLinkManId;
	
	/**
	 * 联系人名字
	 */
	private  String name; 
	
	/**
	 * 职务
	 */
	private  String post;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * QQ号
	 */
	private String QQ;
	
	/**
	 * 联系邮箱
	 */
	private String email;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSupplierLinkManId() {
		return supplierLinkManId;
	}

	public void setSupplierLinkManId(String supplierLinkManId) {
		this.supplierLinkManId = supplierLinkManId;
	}

}
