package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SpecDetailQO extends BaseQo{
	private String skuProductId;

	private String specificationId;
	
	private String specValueId;
	
	private String productId;
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSkuProductId() {
		return skuProductId;
	}

	public void setSkuProductId(String skuProductId) {
		this.skuProductId = skuProductId;
	}

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}

	public String getSpecValueId() {
		return specValueId;
	}

	public void setSpecValueId(String specValueId) {
		this.specValueId = specValueId;
	}
	
	
}
