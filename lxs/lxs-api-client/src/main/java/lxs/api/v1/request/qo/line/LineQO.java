package lxs.api.v1.request.qo.line;

import java.util.Date;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class LineQO extends ApiPayload {

	private boolean isQuerySelectiveLine;
	
	private String saleDate;

	private String lineID;

	private String lineSelectiveID;

	private String name;

	private String startingCity;

	private String type;

	private int pageNO;

	private int pageSize;

	private int order;

	private String orderType;

	// 出游时间
	private Date startTime;

	// 行程天数
	private Integer routeDays;

	// 主题
	private String subjectId;

	private double lowPrice;

	private double highPrice;

	//目的地
	private String destinationCity;

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getLineSelectiveID() {
		return lineSelectiveID;
	}

	public void setLineSelectiveID(String lineSelectiveID) {
		this.lineSelectiveID = lineSelectiveID;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isQuerySelectiveLine() {
		return isQuerySelectiveLine;
	}

	public void setQuerySelectiveLine(boolean isQuerySelectiveLine) {
		this.isQuerySelectiveLine = isQuerySelectiveLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageNO() {
		return pageNO;
	}

	public void setPageNO(int pageNO) {
		this.pageNO = pageNO;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
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

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	

	
}
