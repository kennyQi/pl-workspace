package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.domain.Theme;


/**
 * 用于调用同城获取景区明细请求返回结果
 * @author zhangqy
 *
 */
public class ResultSceneryDetail extends Result {
	/**
	 * 景点Id
	 */
	private Integer sceneryId;
	/**
	 * 景点名称
	 */
	private String sceneryName;
	/**
	 * 景点级别
	 */
	private String grade;
	/**
	 * 景点地址
	 */
	private String sceneryAddress;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 景点简介
	 */
	private String intro;
	/**
	 * 购票需知
	 */
	private String buyNotie;
	/**
	 * 支付类型
	 */
	private String payMode;
	/**
	 * Mapbar经度/百度经度
	 */
	private String lon;
	/**
	 * Mapbar纬度/百度纬度
	 */
	private String lat;
	/**
	 * 景点别名
	 */
	private String sceneryAlias;
	/**
	 * 同程价
	 */
	private Double amountAdvice;
	/**
	 *是否需要证件号
	 */
	private Integer ifUseCard;
	private Integer bookFlag;
	private String address;
	private String buyNotice;
	private Theme theme;
	public Integer getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(Integer sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSceneryAddress() {
		return sceneryAddress;
	}
	public void setSceneryAddress(String sceneryAddress) {
		this.sceneryAddress = sceneryAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getBuyNotie() {
		return buyNotie;
	}
	public void setBuyNotie(String buyNotie) {
		this.buyNotie = buyNotie;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getSceneryAlias() {
		return sceneryAlias;
	}
	public void setSceneryAlias(String sceneryAlias) {
		this.sceneryAlias = sceneryAlias;
	}
	public Double getAmountAdvice() {
		return amountAdvice;
	}
	public void setAmountAdvice(Double amountAdvice) {
		this.amountAdvice = amountAdvice;
	}
	public Integer getIfUseCard() {
		return ifUseCard;
	}
	public void setIfUseCard(Integer ifUseCard) {
		this.ifUseCard = ifUseCard;
	}
	public Integer getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(Integer bookFlag) {
		this.bookFlag = bookFlag;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBuyNotice() {
		return buyNotice;
	}
	public void setBuyNotice(String buyNotice) {
		this.buyNotice = buyNotice;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
	
}
