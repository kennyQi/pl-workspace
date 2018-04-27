package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.dto.product.DealerProductMappingDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.system.Project;

/**
 * 平台商品对照表
 * @author liujz
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_PRODUCT+"DEALER_PRODUCT_MAPPING")
public class DealerProductMapping extends BaseModel {

	
	/**
	 * sku商品
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SKU_PRODUCT_ID")
	private SkuProduct skuProduct;
	
	/**
	 * 平台商品编码
	 */
	@Column(name="DEALER_PRODUCT_CODE",length=64)
	private String dealerProductCode;
	
	/**
	 * 所属项目
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	/**
	 * 所属表格序列
	 */
	@Column(name="SEQUENCE")
	private Integer sequence;
	

	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public String getDealerProductCode() {
		return dealerProductCode;
	}

	public void setDealerProductCode(String dealerProductCode) {
		this.dealerProductCode = dealerProductCode;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void createDealerProductMapping(
			DealerProductMappingDTO dealerProductMappingDTO) {
		setId(UUIDGenerator.getUUID());
		setDealerProductCode(dealerProductMappingDTO.getDealerProductCode());
		project = new Project();
		project.setId(dealerProductMappingDTO.getProjectId());
		setProject(project);
		skuProduct = new SkuProduct();
		skuProduct.setId(dealerProductMappingDTO.getSkuProductId());
		setSkuProduct(skuProduct);
		setSequence(dealerProductMappingDTO.getSequence());
	}
	public void modifyDealerProductMapping(
			DealerProductMappingDTO dealerProductMappingDTO,Project project,SkuProduct skuProduct) {
		setId(dealerProductMappingDTO.getDealerProductMappingId());
		setDealerProductCode(dealerProductMappingDTO.getDealerProductCode());
		setProject(project);
		setSkuProduct(skuProduct);
		setSequence(dealerProductMappingDTO.getSequence());
	}


}
