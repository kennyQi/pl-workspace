package jxc.domain.model.product;

import hg.common.component.BaseModel;
import hg.pojo.command.ModifyProductSkuCommand;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import jxc.domain.model.M;

/**
 * 商品sku表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_PRODUCT+"SKU_PRODUCT")
public class SkuProduct extends BaseModel {

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@OneToMany(mappedBy="skuProduct")
	private List<SpecDetail> specDetails;

	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<SpecDetail> getSpecDetails() {
		return specDetails;
	}

	public void setSpecDetails(List<SpecDetail> specDetails) {
		this.specDetails = specDetails;
	}

	public void createSkuProduct(ModifyProductSkuCommand command,String valueIds,String productCode) {
		String id = productCode;
		setId(id+valueIds);
		product = new Product();
		product.setId(command.getProductId());
		setProduct(product);
	}

	public void create(String skuCode, String id) {
		setId(skuCode);
		product = new Product();
		product.setId(id);
		
		
		
	}
}
