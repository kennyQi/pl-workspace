package slfx.api.v1.request.qo.xl;

import java.util.Date;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：api接口查询线路QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:46:08
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLLineApiQO extends ApiPayload {
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 出发市
	 */
	private String startingCityID;
	/**
	 * 行程天数
	 */
	private Integer routeDays;
	
	/**
	 * 住宿星级
	 */
	private String stayLevel;
	

	/**
	 * 线路团期最高成人价格
	 */
	private Double adultPriceMax;
	
	/**
	 * 线路团期最低成人价格
	 */
	private Double adultPriceMin;
	/**
	 * 线路快照起始时间
	 */
	private Date lineSnapshotBeginDate;
	
	/**
	 * 线路快照结束时间
	 */
	private Date lineSnapshotEndDate;
	
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
	 * 每页记录数
	 */
	private Integer pageSize = 20;
	/**
	 * 页码
	 */
	private Integer pageNo = 1;
	
	public String getStartingCityID() {
		return startingCityID;
	}

	public void setStartingCityID(String startingCityID) {
		this.startingCityID = startingCityID;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
