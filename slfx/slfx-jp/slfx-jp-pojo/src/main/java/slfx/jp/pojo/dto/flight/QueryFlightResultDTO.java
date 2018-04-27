package slfx.jp.pojo.dto.flight;

import java.util.*;

import slfx.jp.pojo.dto.BaseJpDTO;
import slfx.jp.pojo.dto.supplier.yg.CancelRuleDTO;

/**
 * 
 * @类功能说明：航班查询结果DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:54:31
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class QueryFlightResultDTO extends BaseJpDTO{
	
	/** 航班号 */
	private String flightNo;
	
	/** 航空公司全称 */
	private String airCompanyName;
	
	/** 航空公司简称 */
	private String airCompanyShortName;
	
	/** 是否支持网上值机 */
	private Boolean checkInSupport;
	
	/** 机型名称 */
	private String planeTypeName;
	
	/** 型号 */
	private String planeType;
	
	/** 机体 */
	private String bodyType;
	
	/** 最少人数 */
	private int minPassangerNum;
	
	/** 最大人数 */
	private int maxPassangerNum;
	
	/** 飞行时长分钟 */
	private int flyTimeMinute;
	
	/** 共享航班号 */
	private String shareFlightNo;
	
	/** 经停次数 */
	private int stopNum;
	
	/** 有无餐食 */
	private Boolean foodProvide;
	
	/** 机建 */
	private Double airportTax;
	
	/** 燃油 */
	private Double fuelTax;
	
	/** 里程 */
	private int mileage;
	
	/** Y舱价格 */
	private int yPrice;
	
	/** 最低价 */
	private Double minPrice;
	
	/** 最低价舱位类型 */
	private String minPriceClass;

	/**
	 * 航班舱位信息
	 */
	public List<FlightClassInfoDTO> classInfos;
	
	/**
	 * 退改规定
	 */
	public Map<String, CancelRuleDTO> cancelRuleMap;
	
	/**
	 * 舱位政策
	 */
	public Map<String, ClassPolicyDTO> classPolicyMap;
	
	/**
	 * 航班起飞信息
	 */
	public FlightStartInfoDTO flightStartInfo;
	
	/**
	 * 航班到达信息
	 */
	public FlightEndInfoDTO flightEndInfo;

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getAirCompanyShortName() {
		return airCompanyShortName;
	}

	public void setAirCompanyShortName(String airCompanyShortName) {
		this.airCompanyShortName = airCompanyShortName;
	}

	public Boolean getCheckInSupport() {
		return checkInSupport;
	}

	public void setCheckInSupport(Boolean checkInSupport) {
		this.checkInSupport = checkInSupport;
	}

	public String getPlaneTypeName() {
		return planeTypeName;
	}

	public void setPlaneTypeName(String planeTypeName) {
		this.planeTypeName = planeTypeName;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public int getMinPassangerNum() {
		return minPassangerNum;
	}

	public void setMinPassangerNum(int minPassangerNum) {
		this.minPassangerNum = minPassangerNum;
	}

	public int getMaxPassangerNum() {
		return maxPassangerNum;
	}

	public void setMaxPassangerNum(int maxPassangerNum) {
		this.maxPassangerNum = maxPassangerNum;
	}

	public int getFlyTimeMinute() {
		return flyTimeMinute;
	}

	public void setFlyTimeMinute(int flyTimeMinute) {
		this.flyTimeMinute = flyTimeMinute;
	}

	public String getShareFlightNo() {
		return shareFlightNo;
	}

	public void setShareFlightNo(String shareFlightNo) {
		this.shareFlightNo = shareFlightNo;
	}

	public int getStopNum() {
		return stopNum;
	}

	public void setStopNum(int stopNum) {
		this.stopNum = stopNum;
	}

	public Boolean getFoodProvide() {
		return foodProvide;
	}

	public void setFoodProvide(Boolean foodProvide) {
		this.foodProvide = foodProvide;
	}

	public Double getAirportTax() {
		return airportTax;
	}

	public void setAirportTax(Double airportTax) {
		this.airportTax = airportTax;
	}

	public Double getFuelTax() {
		return fuelTax;
	}

	public void setFuelTax(Double fuelTax) {
		this.fuelTax = fuelTax;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getyPrice() {
		return yPrice;
	}

	public void setyPrice(int yPrice) {
		this.yPrice = yPrice;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public String getMinPriceClass() {
		return minPriceClass;
	}

	public void setMinPriceClass(String minPriceClass) {
		this.minPriceClass = minPriceClass;
	}

	public List<FlightClassInfoDTO> getClassInfos() {
		return classInfos;
	}

	public void setClassInfos(List<FlightClassInfoDTO> classInfos) {
		this.classInfos = classInfos;
	}

	public Map<String, CancelRuleDTO> getCancelRuleMap() {
		return cancelRuleMap;
	}

	public void setCancelRuleMap(Map<String, CancelRuleDTO> cancelRuleMap) {
		this.cancelRuleMap = cancelRuleMap;
	}

	public Map<String, ClassPolicyDTO> getClassPolicyMap() {
		return classPolicyMap;
	}

	public void setClassPolicyMap(Map<String, ClassPolicyDTO> classPolicyMap) {
		this.classPolicyMap = classPolicyMap;
	}

	public FlightStartInfoDTO getFlightStartInfo() {
		return flightStartInfo;
	}

	public void setFlightStartInfo(FlightStartInfoDTO flightStartInfo) {
		this.flightStartInfo = flightStartInfo;
	}

	public FlightEndInfoDTO getFlightEndInfo() {
		return flightEndInfo;
	}

	public void setFlightEndInfo(FlightEndInfoDTO flightEndInfo) {
		this.flightEndInfo = flightEndInfo;
	}
	
}