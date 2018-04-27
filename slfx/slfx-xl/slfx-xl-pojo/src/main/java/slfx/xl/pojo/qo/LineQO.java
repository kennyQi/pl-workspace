package slfx.xl.pojo.qo;

import hg.common.component.BaseQo;

import java.util.Date;

/**
 * @类功能说明：线路QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月2日下午5:55:34
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class LineQO extends BaseQo {
	
	/**
	 * 线路名称
	 */
	public String lineName;
	
	/**
	 * 行程id
	 */
	public String dayRouteId;
	
	/**
	 * 线路类别
	 */
	public Integer type;
	
	/**
	 * 创建开始时间
	 */
	private Date createDateFrom;
	
	/**
	 * 创建结束时间
	 */
	private Date createDateTo;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 出发省
	 */
	private String startingProvinceID;
	
	/**
	 * 出发市
	 */
	private String startingCityID;

	/**
	 * 出发地：1、国内；2、国外
	 *只供页面交互使用
	 */
	private String startingDepart;
	
	/**
	 * 线路类别：1、国内线；2、国外线
	 * 只供页面交互使用
	 */
	private String domesticLine;
	
	/**
	 * 线路类型中的省编号
	 */
	private String provinceOfType;
	
	/**
	 * 线路类型中的城市编号
	 */
	private String cityOfType;
	
	/**
	 * 按创建时间排序
	 */
	private Boolean createDateAsc = false;
	
	/**
	 * 线路团期最高成人价格
	 */
	private Double adultPriceMax;
	
	/**
	 * 线路团期最低成人价格
	 */
	private Double adultPriceMin;
	
	/**
	 * 团期开始时间
	 */
	private Date beginDate;
	
	/**
	 * 团期结束时间
	 */
	private Date endDate;
	
	
	/**
	 * 行程天数
	 */
	private Integer routeDays;
	
	/**
	 * 住宿星级
	 */
	private String stayLevel;
	/**
	 * 按推荐指数降序
	 */
	private boolean recommendationLevelAsc = false;
	/**
	 * 按销量降序
	 */
	private boolean salesAsc = false;
	/**
	 * 按价格降序
	 */
	private boolean priceAsc = false;
	/**
	 * 线路快照起始时间
	 */
	private Date lineSnapshotBeginDate;
	/**
	 * 线路快照结束时间
	 */
	private Date lineSnapshotEndDate;
	/**
	 * 线路编号
	 */
	private String number;
	/**
	 * 是否名称模糊查询
	 */
	private boolean isNameLike= true;
	
	public boolean isRecommendationLevelAsc() {
		return recommendationLevelAsc;
	}

	public void setRecommendationLevelAsc(boolean recommendationLevelAsc) {
		this.recommendationLevelAsc = recommendationLevelAsc;
	}

	public boolean isSalesAsc() {
		return salesAsc;
	}

	public void setSalesAsc(boolean salesAsc) {
		this.salesAsc = salesAsc;
	}

	public boolean isPriceAsc() {
		return priceAsc;
	}

	public void setPriceAsc(boolean priceAsc) {
		this.priceAsc = priceAsc;
	}

	public Integer getRouteDays() {
		return routeDays;
	}

	public void setRouteDays(Integer routeDays) {
		this.routeDays = routeDays;
	}

	public String getStayLevel() {
		return stayLevel;
	}

	public void setStayLevel(String stayLevel) {
		this.stayLevel = stayLevel;
	}


	public LineQO(){
		super();
	}
	
	public LineQO(String id){
		super();
		setId(id);
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartingProvinceID() {
		return startingProvinceID;
	}

	public void setStartingProvinceID(String startingProvinceID) {
		this.startingProvinceID = startingProvinceID;
	}

	public String getStartingCityID() {
		return startingCityID;
	}

	public void setStartingCityID(String startingCityID) {
		this.startingCityID = startingCityID;
	}

	public String getStartingDepart() {
		return startingDepart;
	}

	public void setStartingDepart(String startingDepart) {
		this.startingDepart = startingDepart;
	}

	public String getDomesticLine() {
		return domesticLine;
	}

	public void setDomesticLine(String domesticLine) {
		this.domesticLine = domesticLine;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}

	public String getProvinceOfType() {
		return provinceOfType;
	}

	public void setProvinceOfType(String provinceOfType) {
		this.provinceOfType = provinceOfType;
	}

	public Double getAdultPriceMax() {
		return adultPriceMax;
	}

	public void setAdultPriceMax(Double adultPriceMax) {
		this.adultPriceMax = adultPriceMax;
	}

	public Double getAdultPriceMin() {
		return adultPriceMin;
	}

	public void setAdultPriceMin(Double adultPriceMin) {
		this.adultPriceMin = adultPriceMin;
	}

	public Date getLineSnapshotBeginDate() {
		return lineSnapshotBeginDate;
	}

	public void setLineSnapshotBeginDate(Date lineSnapshotBeginDate) {
		this.lineSnapshotBeginDate = lineSnapshotBeginDate;
	}

	public Date getLineSnapshotEndDate() {
		return lineSnapshotEndDate;
	}

	public void setLineSnapshotEndDate(Date lineSnapshotEndDate) {
		this.lineSnapshotEndDate = lineSnapshotEndDate;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public boolean isNameLike() {
		return isNameLike;
	}

	public void setNameLike(boolean isNameLike) {
		this.isNameLike = isNameLike;
	}

	public String getDayRouteId() {
		return dayRouteId;
	}

	public void setDayRouteId(String dayRouteId) {
		this.dayRouteId = dayRouteId;
	}
}