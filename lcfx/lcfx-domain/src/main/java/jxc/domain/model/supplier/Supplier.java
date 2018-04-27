package jxc.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateSupplierCommand;
import hg.pojo.command.ModifySupplierCommand;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

/**
 * 供应商表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SUPPLIER + "SUPPLIER")
public class Supplier extends JxcBaseModel {

	/**
	 * 供应商基础信息
	 */
	@Embedded
	private SupplierBaseInfo baseInfo;

	/**
	 * 供应商联系方式
	 */
	@Embedded
	private SupplierContact contact;

	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 255)
	private String remark;

	/**
	 * 联系人
	 */
	@OneToMany(mappedBy = "supplier")
	private List<SupplierLinkMan> linkManList;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 创建时间
	 */
	@Column(name = "SUPPLIER_CODE", length = 50)
	private String supplierCode;

	public SupplierBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(SupplierBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SupplierContact getContact() {
		return contact;
	}

	public void setContact(SupplierContact contact) {
		this.contact = contact;
	}

	public List<SupplierLinkMan> getLinkManList() {
		return linkManList;
	}

	public void setLinkManList(List<SupplierLinkMan> linkManList) {
		this.linkManList = linkManList;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void createSupplier(CreateSupplierCommand command) {
		
		baseInfo = new SupplierBaseInfo();
		baseInfo.setName(command.getName());
		baseInfo.setType(command.getType());
		baseInfo.setBank(command.getBank());
		baseInfo.setAccount(command.getAccount());
		baseInfo.setURL(command.getURL());
		baseInfo.setLegalPerson(command.getLegalPerson());
		baseInfo.setTax(command.getTax());
		baseInfo.setRegisteredCapital(command.getRegisteredCapital());
		baseInfo.setEstablishDate(command.getEstablishDate());
		
		contact = new SupplierContact();
		contact.setPhone(command.getPhone());
		contact.setAddress(command.getAddress());
		contact.setPostCode(command.getPostCode());
		contact.setEmail(command.getEmail());
		contact.setFax(command.getFax());
		
		setId(UUIDGenerator.getUUID());
		
		setRemark(command.getRemark());
		setCreateDate(new Date());
		setSupplierCode(command.getSupplierCode());
		
		setStatusRemoved(false);
		
	}

	public void modifySupplier(ModifySupplierCommand command) {

		baseInfo.setName(command.getName());
		baseInfo.setType(command.getType());
		baseInfo.setBank(command.getBank());
		baseInfo.setAccount(command.getAccount());
		baseInfo.setURL(command.getURL());
		baseInfo.setLegalPerson(command.getLegalPerson());
		baseInfo.setTax(command.getTax());
		baseInfo.setRegisteredCapital(command.getRegisteredCapital());
		baseInfo.setEstablishDate(command.getEstablishDate());
		
		contact.setPhone(command.getPhone());
		contact.setAddress(command.getAddress());
		contact.setPostCode(command.getPostCode());
		contact.setEmail(command.getEmail());
		contact.setFax(command.getFax());
		
		setRemark(command.getRemark());
		
		setSupplierCode(command.getSupplierCode());
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}


}
