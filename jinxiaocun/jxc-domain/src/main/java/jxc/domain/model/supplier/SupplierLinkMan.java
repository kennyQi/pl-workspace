package jxc.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.dto.supplier.SupplierLinkManDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;

import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SUPPLIER+"LINKMAN")
public class SupplierLinkMan extends BaseModel {

	/**
	 * 所属供应商
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;
	
	/**
	 * 联系人名字
	 */
	@Column(name="LINKMAN_NAME",length=10)
	private  String name; 
	
	/**
	 * 职务
	 */
	@Column(name="LINKMAN_POST",length=50)
	private  String post;
	
	/**
	 * 手机号
	 */
	@Column(name="LINKMAN_MOBILE",length=11)
	private String mobile;
	
	/**
	 * 联系电话
	 */
	@Column(name="LINKMAN_PHONE",length=15)
	private String phone;
	
	/**
	 * QQ号
	 */
	@Column(name="LINKMAN_QQ",length=20)
	private String QQ;
	
	/**
	 * 联系邮箱
	 */
	@Column(name="LINKMAN_EMAIL",length=100)
	private String email;

	/**
	 * 是否是默认联系人
	 */
	@Type(type = "yes_no")
	@Column(name = "DEFAULT_LINK_MAN")
	private Boolean defaultLinkMan;
	
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Boolean getDefaultLinkMan() {
		return defaultLinkMan;
	}

	public void setDefaultLinkMan(Boolean defaultLinkMan) {
		this.defaultLinkMan = defaultLinkMan;
	}

	public void createLinkMan(SupplierLinkManDTO linkManDTO,Supplier supplier) {
		setId(UUIDGenerator.getUUID());
		setName(linkManDTO.getName());
		setQQ(linkManDTO.getQQ());
		setPost(linkManDTO.getPost());
		setEmail(linkManDTO.getEmail());
		setMobile(linkManDTO.getMobile());
		setPhone(linkManDTO.getPhone());
		setSupplier(supplier);
	}

	public void modifyLinkMan(SupplierLinkManDTO linkManDTO) {
		setName(linkManDTO.getName());
		setQQ(linkManDTO.getQQ());
		setPost(linkManDTO.getPost());
		setEmail(linkManDTO.getEmail());
		setMobile(linkManDTO.getMobile());
		setPhone(linkManDTO.getPhone());
	}
	
}
