package hsl.pojo.command.ad;

@SuppressWarnings("serial")
public class UpdateSpecCommand extends HslUpdateAdCommand{
	/**
	 * 原价价格
	 */
	private Double price;
	/**
	 * 特价价格
	 */
	private Double specPrice;
	/**
	 * 景点类型
	 */
	private int scenicType;
	/**
	 * 广告ID
	 */
	private String adId;
	
	/**
	 * 栏目内容ID
	 */
	private String contentId;
	
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSpecPrice() {
		return specPrice;
	}
	public void setSpecPrice(Double specPrice) {
		this.specPrice = specPrice;
	}
	public int getScenicType() {
		return scenicType;
	}
	public void setScenicType(int scenicType) {
		this.scenicType = scenicType;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	
}
