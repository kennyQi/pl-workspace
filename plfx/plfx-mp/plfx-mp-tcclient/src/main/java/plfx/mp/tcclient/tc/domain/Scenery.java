package plfx.mp.tcclient.tc.domain;

import java.util.List;

/**
 * 景区
 * @author zhangqy
 *
 */
public class Scenery {
	/**
	 * 景点名称
	 */
	private String sceneryName;
	/**
	 * 景点Id
	 */
	private String sceneryId;
	/**
	 * 景点地址
	 */
	private String sceneryAddress;
	/**
	 * 景点简介
	 */
	private String scenerySummary;
	/**
	 * 图片地址
	 */
	private String imgPath;
	/**
	 * 省份Id
	 */
	private Integer provinceId; 
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 城市Id
	 */
	private Integer cityId;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 行政区划（县）Id
	 */
	private Integer countyId;
	/**
	 * 行政区划（县）名称
	 */
	private String countyName;
	/**
	 * 等级ID
	 */
	private String gradeId;
	/**
	 * 点评数
	 */
	private Integer commentCount;
	/**
	 * 问答数
	 */
	private Integer questionCount;
	/**
	 * 博客数量
	 */
	private Integer blogCount;
	/**
	 * 浏览次数
	 */
	private Integer viewCount;
	/**
	 * 距离
	 */
	private Double distance;
	/**
	 * 经度
	 */
	private Double lon;
	/**
	 * 纬度
	 */
	private Double lat;
	/**
	 * 可预订情况
	 */
	private Integer bookFlag;
	//================同程文档里面没有，不知道啥子意思
	private Integer sceneryAmount;
	private Integer adviceAmount;
	private Integer amount;
	private Integer amountAdv;
	//=====================================
	/**
	 * 景点主题列表
	 */
	private List<Theme> themeList;
	/**
	 * 适合人群列表
	 */
	private List<Impression> impressionList;
	/**
	 * 游客印象列表
	 */
	private List<Suitherd> suitherdList;
	/**
	 * 景区别名
	 */
	private List<Na> naList;
	
	public static final Integer BOOK_UN_USED=-1;//暂时下线
	public static final Integer BOOK_NO_ALLOW=0;//不可预定
	public static final Integer BOOK_IS_ALLOW=1;//可预定
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryAddress() {
		return sceneryAddress;
	}
	public void setSceneryAddress(String sceneryAddress) {
		this.sceneryAddress = sceneryAddress;
	}
	public String getScenerySummary() {
		return scenerySummary;
	}
	public void setScenerySummary(String scenerySummary) {
		this.scenerySummary = scenerySummary;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Integer getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(Integer bookFlag) {
		this.bookFlag = bookFlag;
	}
	public List<Theme> getThemeList() {
		return themeList;
	}
	public void setThemeList(List<Theme> themeList) {
		this.themeList = themeList;
	}
	public List<Impression> getImpressionList() {
		return impressionList;
	}
	public void setImpressionList(List<Impression> impressionList) {
		this.impressionList = impressionList;
	}
	public List<Suitherd> getSuitherdList() {
		return suitherdList;
	}
	public void setSuitherdList(List<Suitherd> suitherdList) {
		this.suitherdList = suitherdList;
	}
	public List<Na> getNaList() {
		return naList;
	}
	public void setNaList(List<Na> naList) {
		this.naList = naList;
	}
	public Integer getSceneryAmount() {
		return sceneryAmount;
	}
	public void setSceneryAmount(Integer sceneryAmount) {
		this.sceneryAmount = sceneryAmount;
	}
	public Integer getAdviceAmount() {
		return adviceAmount;
	}
	public void setAdviceAmount(Integer adviceAmount) {
		this.adviceAmount = adviceAmount;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getAmountAdv() {
		return amountAdv;
	}
	public void setAmountAdv(Integer amountAdv) {
		this.amountAdv = amountAdv;
	}
	
	
	
}
