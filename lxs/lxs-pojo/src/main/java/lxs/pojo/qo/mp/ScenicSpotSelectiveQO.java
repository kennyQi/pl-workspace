package lxs.pojo.qo.mp;

import java.util.List;

import hg.common.component.BaseQo;

/**
 * 精选线路查询的QO
 * 
 * @author guok
 * 
 */
@SuppressWarnings("serial")
public class ScenicSpotSelectiveQO extends BaseQo {
	
	private String ScenicSpotID;

	private int order;

	private String orderType;

	private String name;

	private String startingCity;

	private String type;

	private int sort;

	private List<String> staringCities;

	private Integer forSale;

	public List<String> getStaringCities() {
		return staringCities;
	}

	public void setStaringCities(List<String> staringCities) {
		this.staringCities = staringCities;
	}

	/**
	 * 起始时间
	 */
	private String beginTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartingCity() {
		return startingCity;
	}

	public void setStartingCity(String startingCity) {
		this.startingCity = startingCity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getForSale() {
		return forSale;
	}

	public void setForSale(Integer forSale) {
		this.forSale = forSale;
	}

	public String getScenicSpotID() {
		return ScenicSpotID;
	}

	public void setScenicSpotID(String scenicSpotID) {
		ScenicSpotID = scenicSpotID;
	}

}
