package lxs.pojo.qo.line;

import java.util.Date;
import java.util.List;

import hg.common.component.BaseQo;

/**
 * 线路查询的QO
 * 
 * @author yuqz
 * 
 */
@SuppressWarnings("serial")
public class LineQO extends BaseQo {

	/**
	 * 出行月份
	 */
	private String saleDate;
	
	/**
	 * 获取热门目的地标准
	 */
	private String GetHotDestinationCity;
	
	/**
	 * 出行日期大于五天标志位
	 */
	private String routeDays5More;
	
	/**
	 * 目的地
	 */
	private String destinationCity;
	
	// 行程天数
	private Integer routeDays;

	// 主题
	private String subjectId;

	private double lowPrice;

	private double highPrice;

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
	private String isSale;

	private String start_province;

	private List<String> startingProvince;

	private List<String> lineIDs;

	
	public List<String> getLineIDs() {
		return lineIDs;
	}

	public void setLineIDs(List<String> lineIDs) {
		this.lineIDs = lineIDs;
	}

	private int localStatus;

	/**
	 * 运营端展示线路列表条件
	 */
	public final static int NOT_DEL = 100;

	/**
	 * 显示
	 */
	public final static int SHOW = 101;
	
	/**
	 * 隐藏
	 */
	public final static int HIDE = 102;

	private int sort;

	/**
	 * 根据置顶排序
	 */
	public final static int QUERY_WITH_TOP = 100;

	/**
	 * 用于查询江浙沪出发地
	 */
	private List<String> ProvinceList;

	public List<String> getProvinceList() {
		return ProvinceList;
	}

	public void setProvinceList(List<String> provinceList) {
		ProvinceList = provinceList;
	}

	public List<String> getStartingProvince() {
		return startingProvince;
	}

	public void setStartingProvince(List<String> startingProvince) {
		this.startingProvince = startingProvince;
	}

	public String getStart_province() {
		return start_province;
	}

	public void setStart_province(String start_province) {
		this.start_province = start_province;
	}

	public String getIsSale() {
		return isSale;
	}

	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}

	private int forSale;

	private String forSaletype;

	public String getForSaletype() {
		return forSaletype;
	}

	public void setForSaletype(String forSaletype) {
		this.forSaletype = forSaletype;
	}

	/**
	 * 排序类型 0为默认排序 1为推荐指数排序 2为销量排序 3为价格排序
	 */
	private Integer orderKind = 0;
	/**
	 * 推荐指数排序1
	 */
	public final static Integer ORDERKIND_RECOMMENDATIONLEVEL = 1;
	/**
	 * 销量排序2
	 */
	public final static Integer ORDERKIND_SALES = 2;
	/**
	 * 价格排序3
	 */
	public final static Integer ORDERKIND_PRICE = 3;
	/**
	 * 排序类型 默认asc
	 */
	private String orderType;
	/**
	 * 升序
	 */
	public final static Boolean ORDERTYPE_ASC = false;
	/**
	 * 降序
	 */
	public final static Boolean ORDERTYPE_DESC = true;

	/**
	 * 发布开始时间
	 */
	private String startTime;

	/**
	 * 发布截止时间
	 */
	private String endTime;

	private int order;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
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

	public int getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(int localStatus) {
		this.localStatus = localStatus;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Integer getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(Integer routeDays) {
		this.routeDays = routeDays;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}

	public String getGetHotDestinationCity() {
		return GetHotDestinationCity;
	}

	public void setGetHotDestinationCity(String getHotDestinationCity) {
		GetHotDestinationCity = getHotDestinationCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getRouteDays5More() {
		return routeDays5More;
	}

	public void setRouteDays5More(String routeDays5More) {
		this.routeDays5More = routeDays5More;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	
}
