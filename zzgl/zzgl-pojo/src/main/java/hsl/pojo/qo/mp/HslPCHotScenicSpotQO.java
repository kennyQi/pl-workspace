package hsl.pojo.qo.mp;
@SuppressWarnings("serial")
public class HslPCHotScenicSpotQO extends HslADQO{
	/**
	 * 热门景点名称
	 */
	private String name;
	/**
	 * 景点id
	 */
	private String scenicSpotId;
	/**
	 * 广告ID
	 */
	private String adId;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	

}
