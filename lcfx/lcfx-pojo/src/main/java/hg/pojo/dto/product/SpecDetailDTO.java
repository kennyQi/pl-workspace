package hg.pojo.dto.product;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SpecDetailDTO implements Serializable{

	/**
	 * 商品id
	 */
	private String productId;
	/**
	 * 规格id
	 */
	private String specificationId;
	
	/**
	 * 规格值id
	 */
	private String specValueId;
	/**
	 * sku编码
	 */
	private String skuProductId;
	

	public String getSkuProductId() {
		return skuProductId;
	}

	public void setSkuProductId(String skuProductId) {
		this.skuProductId = skuProductId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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
