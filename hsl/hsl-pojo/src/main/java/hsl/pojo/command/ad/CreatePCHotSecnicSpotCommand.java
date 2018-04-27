package hsl.pojo.command.ad;

@SuppressWarnings("serial")
public class CreatePCHotSecnicSpotCommand extends HslCreateAdCommand{
	/**
	 * 景点id
	 */
	private String scenicSpotId;
	/**
	 * 广告id
	 */
	private String adId;
	
	/**
	 * 
	 ****************** 景点部分信息 **********************************/
	
	/**
	 * 景点名称
	 */
	private String name;
	
	/**
	 * 景点简介
	 */
	private String intro;

	/**
	 * 景点首图
	 */
	private String image;

	/**
	 * 景点级别
	 */
	private String grade;

	/**
	 * 景点别名
	 */
	private String alias;
	
	/**
	 * 景区地址
	 */
	private String address;
	/**
	 * 所在省
	 */
	private String province;
	/**
	 * 所在城市
	 */
	private String city;
	
	/**
	 * 交通指南
	 */
	private String traffic;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

}
