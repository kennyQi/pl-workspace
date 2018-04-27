package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

public class RebateIntervalSQO extends BaseSPIQO{

	/**  */
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 商品
	 */
	private String productId;
	
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	private Boolean isImplement;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public Boolean getIsImplement() {
		return isImplement;
	}


	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}
	
	     
	
}
