package hg.pojo.command;

import java.util.List;

/**
 * 编辑商品sku
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifyProductSkuCommand extends JxcCommand {

	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 规格值id列表
	 */
	private List<String> specValueIdList;
	
	/**
	 * 规格id列表
	 */
	private String specificationIdList;
	
	/**
	 * sku列表
	 */
	private List<String> skuList;

	public List<String> getSpecValueIdList() {
		return specValueIdList;
	}

	public void setSpecValueIdList(List<String> specValueIdList) {
		this.specValueIdList = specValueIdList;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<String> skuList) {
		this.skuList = skuList;
	}

	public String getSpecificationIdList() {
		return specificationIdList;
	}

	public void setSpecificationIdList(String specificationIdList) {
		this.specificationIdList = specificationIdList;
	}


}
