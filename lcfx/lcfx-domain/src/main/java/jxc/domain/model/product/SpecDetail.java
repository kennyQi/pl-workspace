package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;

/**
 * 商品详情表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_PRODUCT + "SKU_SPEC_DETAIL")
public class SpecDetail extends BaseModel {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SKU_PRODUCT_ID")
	private SkuProduct skuProduct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIFICATION_ID")
	private Specification specification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPEC_VALUE_ID")
	private SpecValue specValue;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public SpecValue getSpecValue() {
		return specValue;
	}

	public void setSpecValue(SpecValue specValue) {
		this.specValue = specValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void createSpecDetail(String[] detail) {
		setId(UUIDGenerator.getUUID());
		setCreateDate(new Date());
		skuProduct = new SkuProduct();
		skuProduct.setId(detail[0]);
		setSkuProduct(skuProduct);
		specification = new Specification();
		specification.setId(detail[1]);
		setSpecification(specification);
		specValue = new SpecValue();
		specValue.setId(detail[2]);
		setSpecValue(specValue);
	}

	public void create(SpecValue specVal, SkuProduct skuProd) {

		specification = specVal.getSpecification();
		createDate = new Date();
		specValue = specVal;
		skuProduct = skuProd;
		setId(UUIDGenerator.getUUID());

	}

}
