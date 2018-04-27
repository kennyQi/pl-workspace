package hg.pojo.dto.product;

public class DealerProductMappingDTO {

	/**
	 * 平台商品对照id
	 */
	private String dealerProductMappingId;
	
	/**
	 * sku商品id
	 */
	private String skuProductId;
	
	/**
	 * 平台商品编码
	 */
	private String dealerProductCode;
	
	/**
	 * 项目id
	 */
	private String projectId;
	
	/**
	 * 所属表序列
	 */
	private Integer sequence;

	public String getSkuProductId() {
		return skuProductId;
	}

	public void setSkuProductId(String skuProductId) {
		this.skuProductId = skuProductId;
	}

	public String getDealerProductCode() {
		return dealerProductCode;
	}

	public void setDealerProductCode(String dealerProductCode) {
		this.dealerProductCode = dealerProductCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDealerProductMappingId() {
		return dealerProductMappingId;
	}

	public void setDealerProductMappingId(String dealerProductMappingId) {
		this.dealerProductMappingId = dealerProductMappingId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}
