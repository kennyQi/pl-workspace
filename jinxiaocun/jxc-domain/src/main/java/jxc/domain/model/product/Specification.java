package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateSpecificationCommand;
import hg.pojo.command.ModifySpecificationCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.util.CodeUtil;

/**
 * 商品规格快照表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "SPECIFICATION")
public class Specification extends JxcBaseModel {

	/**
	 * 商品类别
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private ProductCategory productCategory;

	/**
	 * 规格编码
	 */
	@Column(name = "SPEC_CODE", length = 4)
	private String specCode;

	/**
	 * 属性名称
	 */
	@Column(name = "SPEC_NAME", length =30)
	private String specName;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 规格状态
	 */
	@Embedded
	private SpecificationStatus status;

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SpecificationStatus getStatus() {
		return status;
	}

	public void setStatus(SpecificationStatus status) {
		this.status = status;
	}

	public String getSpecCode() {
		return specCode;
	}

	public void setSpecCode(String specCode) {
		this.specCode = specCode;
	}

	public void createSpecificationCommand(CreateSpecificationCommand command) {
		productCategory = new ProductCategory();
		productCategory.setId(command.getCategoryId());
		status = new SpecificationStatus();
		status.setUsing(true);
		setProductCategory(productCategory);
		setStatus(status);
		setId(UUIDGenerator.getUUID());
		setSpecCode(CodeUtil.createSpecificationCode());
		setSpecName(command.getSpecificationName());
		setCreateDate(new Date());
		
		setStatusRemoved(false);
	}

	public void modifySpecificationCommand(ModifySpecificationCommand command,ProductCategory productCategory) {
		setProductCategory(productCategory);
		setId(command.getSpecificationId());
		setSpecName(command.getSpecificationName());
		setCreateDate(command.getCreateDate());
		setSpecCode(command.getSpecificationCode());
		status = new SpecificationStatus(command.getUsing());
		setStatus(status);
	}
}
