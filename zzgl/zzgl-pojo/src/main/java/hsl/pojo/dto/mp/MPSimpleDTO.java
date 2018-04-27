package hsl.pojo.dto.mp;
/**
 * 热卖排行和点击记录的DTO
 * @author zhuxy
 *
 */

public class MPSimpleDTO {
	/**
	 * 景点名称
	 */
	private String scenicSpotName;
	/**
	 * 景点政策中的最低价
	 */
	private Double price;
	
	/**
	 * 景点ID
	 */
	private String scenicSpotId;
	
	/**
	 * 圖片地址
	 */
	private String imageUrl;


	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getScenicSpotName() {
		return scenicSpotName;
	}
	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	

}
