package hsl.pojo.qo.line;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 线路查询的QO
 * @author yuqz
 *
 */
@SuppressWarnings("serial")
public class HslLineQO extends BaseQo {
	/**
	 * 查询关键字
	 */
	private String searchName;
	/**
	 * 是否加价格查询
	 */
	private String havePrice;
	/**
	 * 最低价
	 */
	private Double minPrice;
	/**
	 * 最高价
	 */
	private Double maxPrice;
	/**
	 * 出发时间-开始时间
	 */
	private Date beginDateTime;
	/**
	 * 出发时间-结束时间
	 */
	private Date endDateTime;
	/**
	 * 行程天数
	 */
	private Integer dayCount;
	/**
	 * 0，等于；-1，小于；1,大于；
	 */
	private int scope;
	/**
	 * 出发城市code
	 */
	private String startCity;
	/**
	 * 出发城市名称
	 */
	private String startCityName;
	
	/**
	 * 目的地城市code
	 */
	private String endCity;
	
	/**
	 * 目的地城市名称
	 */
	private String endCityName;
	
	/**
	 * 酒店星级
	 */
	private String level;
	/**
	 * 是否加载价格日历
	 */
	private boolean getDateSalePrice = false;
	/**
	 * 是否加载图片
	 */
	private boolean getPictures = false;
	/**
	 * 是否上架
	 */
	
	private int forSale = 1;
	/**
	 * 排序类型
	 * 0为默认排序
	 * 1为推荐指数排序
	 * 2为销量排序
	 * 3为价格排序
	 */
	private Integer orderKind=0;
	/**
	 * 推荐指数排序1
	 */
	public final static Integer ORDERKIND_RECOMMENDATIONLEVEL=1;
	/**
	 * 销量排序2
	 */
	public final static Integer ORDERKIND_SALES=2;
	/**
	 * 价格排序3
	 */
	public final static Integer ORDERKIND_PRICE=3;
	/**
	 * 排序类型
	 * 默认asc
	 */
	private Boolean orderType=false;
	/**
	 * 升序
	 */
	public final static Boolean ORDERTYPE_ASC=false;
	/**
	 * 降序
	 */
	public final static Boolean ORDERTYPE_DESC=true;
	
	/**
	 * 发布开始时间
	 */
	private String startTime;
	
	/**
	 * 发布截止时间
	 */
	private String endTime;
	
	public String getSearchName() {
		return searchName;
	}
	
	
	public String getEndCity() {
		return endCity;
	}


	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}


	public String getEndCityName() {
		return endCityName;
	}


	public void setEndCityName(String endCityName) {
		this.endCityName = endCityName;
	}


	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Date getBeginDateTime() {
		return beginDateTime;
	}
	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	public Date getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	public Integer getDayCount() {
		return dayCount;
	}
	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public boolean getGetDateSalePrice() {
		return getDateSalePrice;
	}
	public void setGetDateSalePrice(boolean getDateSalePrice) {
		this.getDateSalePrice = getDateSalePrice;
	}
	public boolean getGetPictures() {
		return getPictures;
	}
	public void setGetPictures(boolean getPictures) {
		this.getPictures = getPictures;
	}
	public String getStartCityName() {
		return startCityName;
	}
	public void setStartCityName(String startCityName) {
		this.startCityName = startCityName;
	}
	public String getHavePrice() {
		return havePrice;
	}
	public void setHavePrice(String havePrice) {
		this.havePrice = havePrice;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public int getForSale() {
		return forSale;
	}
	public void setForSale(int forSale) {
		this.forSale = forSale;
	}
	public Boolean getOrderType() {
		return orderType;
	}
	public void setOrderType(Boolean orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderKind() {
		return orderKind;
	}
	public void setOrderKind(Integer orderKind) {
		this.orderKind = orderKind;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
