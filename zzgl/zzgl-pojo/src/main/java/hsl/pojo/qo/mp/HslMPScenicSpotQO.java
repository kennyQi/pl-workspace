package hsl.pojo.qo.mp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class HslMPScenicSpotQO extends BaseQo {
	
	/**
	 * 景区id精确查询
	 */
	private String scenicSpotId;

	/**
	 * 查询内容
	 */
	private String content;
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 最高价
	 */
	private String maxPrice;
	
	/**
	 * 最低价
	 */
	private String minPrice;
	
	/**
	 * 景点级别
	 */
	private String grade;
	
	/**
	 * 排序条件
	 * 0:默认排序;1:价格从高到低2:价格从低到高
	 */
	private Integer orderBy=0;
	
	/**
	 * 是否按景区名称模糊查询
	 */
	private Boolean byName=false;

	/**
	 * 是否按景区所在地模糊查询
	 */
	private Boolean byArea=false;

	/**
	 * 查询热门景区，除分页外，其余所有条件为空
	 */
	private Boolean hot=false;

	/**
	 * 返回页码
	 */
	private Integer pageNo=1;

	/**
	 * 返回条目
	 */
	private Integer pageSize=1;
	
	/**
	 * 是否加载政策须知
	 */
	private boolean tcPolicyNoticeFetchAble = false;
	
	/**
	 * 是否加载相关图片
	 */
	private boolean imagesFetchAble = false;
	
	/**
	 * 是否是当前热门景点
	 */
	private Boolean isOpen=true;
	
	/**
	 * 热门景点id
	 */
	private String hotScenicSpotId;
	/*
	 * 用于前台检索省份id
	 */
	private int provinceId;
	/*
	 * 用于前台检索城市id
	 */
	private int cityId;
	private String name;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean getByName() {
		return byName;
	}

	public void setByName(Boolean byName) {
		this.byName = byName;
	}

	public Boolean getByArea() {
		return byArea;
	}

	public void setByArea(Boolean byArea) {
		this.byArea = byArea;
	}

	public Boolean getHot() {
		return hot;
	}

	public void setHot(Boolean hot) {
		this.hot = hot;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isTcPolicyNoticeFetchAble() {
		return tcPolicyNoticeFetchAble;
	}

	public void setTcPolicyNoticeFetchAble(boolean tcPolicyNoticeFetchAble) {
		this.tcPolicyNoticeFetchAble = tcPolicyNoticeFetchAble;
	}

	public boolean isImagesFetchAble() {
		return imagesFetchAble;
	}

	public void setImagesFetchAble(boolean imagesFetchAble) {
		this.imagesFetchAble = imagesFetchAble;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getHotScenicSpotId() {
		return hotScenicSpotId;
	}

	public void setHotScenicSpotId(String hotScenicSpotId) {
		this.hotScenicSpotId = hotScenicSpotId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

}
