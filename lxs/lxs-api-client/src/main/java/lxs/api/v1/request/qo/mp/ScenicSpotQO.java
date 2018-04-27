package lxs.api.v1.request.qo.mp;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class ScenicSpotQO extends ApiPayload {

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
	private String subjectID;

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
	 * 每页数量
	 */
	private String pageSize;

	/**
	 * 页码
	 */
	private String pageNO;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
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

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNO() {
		return pageNO;
	}

	public void setPageNO(String pageNO) {
		this.pageNO = pageNO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
