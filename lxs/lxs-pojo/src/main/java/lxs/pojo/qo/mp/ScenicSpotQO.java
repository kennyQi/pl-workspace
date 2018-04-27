package lxs.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class ScenicSpotQO extends BaseQo {

	private Integer versionNO;

	/** 大于 */
	public final static Integer GREATER_THAN = 1;
	/** 小于 */
	public final static Integer LESS_THAN = -1;

	private Integer versionType = 0;
	/**
	 * 景区名称
	 */
	private String name;
	/**
	 * 景区级别(AAAAA级、AAAA级、AAA级、AA级、A级)
	 */
	private String level;

	/**
	 * 景区主题
	 */
	private String theme;

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
	 * 联票(与经销商)游玩价 最低价
	 */
	private Double playPriceLow;

	/**
	 * 联票(与经销商)游玩价 最高价
	 */
	private Double playPriceHigh;

	/**
	 * 排序条件 sales:销量 price:价格
	 */
	private String orderBy;

	/**
	 * 排序顺序 l2h:low to high h2l:high to low
	 */
	private String sort;
	/**
	 * 本地状态 0：正常显示 1：隐藏 2：删除
	 */
	private Integer localStatus;

	public Integer getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(Integer versionNO) {
		this.versionNO = versionNO;
	}

	public Integer getVersionType() {
		return versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Double getPlayPriceLow() {
		return playPriceLow;
	}

	public void setPlayPriceLow(Double playPriceLow) {
		this.playPriceLow = playPriceLow;
	}

	public Double getPlayPriceHigh() {
		return playPriceHigh;
	}

	public void setPlayPriceHigh(Double playPriceHigh) {
		this.playPriceHigh = playPriceHigh;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(Integer localStatus) {
		this.localStatus = localStatus;
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

}