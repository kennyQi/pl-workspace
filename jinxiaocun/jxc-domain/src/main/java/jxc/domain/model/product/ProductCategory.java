package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateCategoryCommand;
import hg.pojo.command.ModifyCategoryCommand;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.util.CodeUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 商品分类快照表
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_PRODUCT+"CATEGORY")
public class ProductCategory extends JxcBaseModel {

	/**
	 * 分类编码
	 */
	@Column(name="CATEGORY_CODE",length=4)
	private String categoryCode;
	
	/**
	 * 分类名称
	 */
	@Column(name="CATEGORY_NAME",length=20)
	private String name;
	
	/**
	 * 父类id
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private ProductCategory parentCategory;
	
	/**
	 * 属性列表
	 */
	@OneToMany(mappedBy="productCategory")
	private List<Specification> specList;
	
	/**
	 * 分类状态
	 */
	@Embedded
	private CategoryStatus status;
	
	/**
	 * 创建时间
	 */
	@Column(name="CREATE_DATE",columnDefinition=M.DATE_COLUM)
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(ProductCategory parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Specification> getSpecList() {
		return specList;
	}

	public void setSpecList(List<Specification> specList) {
		this.specList = specList;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public CategoryStatus getStatus() {
		return status;
	}

	public void setStatus(CategoryStatus status) {
		this.status = status;
	}
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void createCategory(CreateCategoryCommand command){
		setName(command.getName());
		setCategoryCode(CodeUtil.createCategoryCode());
		setId(UUIDGenerator.getUUID());
		if(StringUtils.isNotBlank(command.getParentCategoryId())){
			parentCategory = new ProductCategory();
			parentCategory.setId(command.getParentCategoryId());
		}
		setParentCategory(parentCategory);
		status = new CategoryStatus();
		status.setUsing(command.getUsing());
		setStatus(status);
		setCreateDate(new Date());
		
		setStatusRemoved(false);
	}
	
	
	public void modifyCategory(ModifyCategoryCommand command,ProductCategory parentCategory){
		
		setName(command.getName());
		setId(command.getCategoryId());
		setParentCategory(parentCategory);
		status = new CategoryStatus();
		status.setUsing(command.getUsing());
		setStatus(status);
		setCreateDate(command.getCreateDate());
	}
}
