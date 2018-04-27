package hg.pojo.command;


@SuppressWarnings("serial")
public class ModifyProductImagesCommand extends JxcCommand {

	/**
	 * 商品id
	 */
	private String productId;
	
	/**
	 * 商品正面图
	 */
	private String frontImageId;
	
	/**
	 * 商品背面图
	 */
	private String backImageId;
	
	/**
	 * 商品细节图
	 */
	private String detailImageId;
	
	/**
	 * 商品配件图
	 */
	private String fittingsImageId;
	
	/**
	 * 商品其他图
	 */
	private String otherOneImageId;
	
	/**
	 * 商品其他图
	 */
	private String otherTwoImageId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getFrontImageId() {
		return frontImageId;
	}

	public void setFrontImageId(String frontImageId) {
		this.frontImageId = frontImageId;
	}

	public String getBackImageId() {
		return backImageId;
	}

	public void setBackImageId(String backImageId) {
		this.backImageId = backImageId;
	}

	public String getDetailImageId() {
		return detailImageId;
	}

	public void setDetailImageId(String detailImageId) {
		this.detailImageId = detailImageId;
	}

	public String getFittingsImageId() {
		return fittingsImageId;
	}

	public void setFittingsImageId(String fittingsImageId) {
		this.fittingsImageId = fittingsImageId;
	}

	public String getOtherOneImageId() {
		return otherOneImageId;
	}

	public void setOtherOneImageId(String otherOneImageId) {
		this.otherOneImageId = otherOneImageId;
	}

	public String getOtherTwoImageId() {
		return otherTwoImageId;
	}

	public void setOtherTwoImageId(String otherTwoImageId) {
		this.otherTwoImageId = otherTwoImageId;
	}
	
}
