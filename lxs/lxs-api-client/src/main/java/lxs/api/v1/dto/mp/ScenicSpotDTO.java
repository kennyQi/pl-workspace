package lxs.api.v1.dto.mp;

public class ScenicSpotDTO {

	/**
	 * 景区名称
	 */
	private String scenicSpotID;

	/**
	 * 景区名称
	 */
	private String name;

	/**
	 * 景区简称
	 */
	private String shortName;

	private String intro;

	/**
	 * 景区级别(AAAAA级、AAAA级、AAA级、AA级、A级)
	 */
	private String level;

	/**
	 * 单票、联票门市价/市场票面价
	 */
	private Double rackRate;

	/**
	 * 联票(与经销商)游玩价
	 */
	private Double playPrice;

	/**
	 * 图片地址
	 */
	private String url;

	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getScenicSpotID() {
		return scenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		this.scenicSpotID = scenicSpotID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Double getRackRate() {
		return rackRate;
	}

	public void setRackRate(Double rackRate) {
		this.rackRate = rackRate;
	}

	public Double getPlayPrice() {
		return playPrice;
	}

	public void setPlayPrice(Double playPrice) {
		this.playPrice = playPrice;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
