package hg.dzpw.dealer.client.dto.scenicspot;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明:景区基本信息
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:08:06
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class ScenicSpotBaseInfoDTO extends EmbeddDTO {

	/**
	 * 景区名称
	 */
	private String name;

	/**
	 * 景区简称
	 */
	private String shortName;
	
	/**
	 * 景区别名
	 */
	private String aliasName;

	/**
	 * 景区代码
	 */
	private String code;

	/**
	 * 景区级别(AAAAA级、AAAA级、AAA级、AA级、A级)
	 */
	private String level;
	
	/**
	 * 建筑部级别(国家级风景名胜区，省级风景名胜区，其他)
	 */
	private String buildLevel;
	
	/**
	 * 景区分类
	 */
	private Integer classify;
	
	/**
	 * 景区特色
	 */
	private String feature;
	
	/**
	 * 景区主题
	 */
	private String theme;
	
	/**
	 * 保存景区主题值,非数据库字段 存储静态数据THEME_TYPE的key
	 */
	private String[] themeValue;
	/**
	 * 开放时间(每天9:30:00-17:30:00)
	 */
	private String openTime;

	/**
	 * 景区网址
	 */
	private String webSite;

	/**
	 * 景区介绍
	 */
	private String intro;

	/**
	 * 预定须知
	 */
	private String preNotice;
	
	/**
	 * 所在省
	 */
	private String provinceId;

	/**
	 * 所在市
	 */
	private String cityId;

	/**
	 * 所在区
	 */
	private String areaId;

	/**
	 * 街道地址
	 */
	private String street;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 门票的默认有效天
	 */
	private Integer ticketDefaultValidDays = 1;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getTicketDefaultValidDays() {
		return ticketDefaultValidDays;
	}

	public void setTicketDefaultValidDays(Integer ticketDefaultValidDays) {
		this.ticketDefaultValidDays = ticketDefaultValidDays;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(String buildLevel) {
		this.buildLevel = buildLevel;
	}

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getPreNotice() {
		return preNotice;
	}

	public void setPreNotice(String preNotice) {
		this.preNotice = preNotice;
	}

	public String[] getThemeValue() {
		return themeValue;
	}

	public void setThemeValue(String[] themeValue) {
		this.themeValue = themeValue;
	}

}