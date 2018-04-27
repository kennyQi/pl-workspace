package plfx.yeexing.pojo.dto.flight;



import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;


/***
 * 
 * @类功能说明：易行天下航班查询信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月15日上午9:32:57
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingFlightDTO extends BaseJpDTO{
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 始发地名称
	 */
	private String orgCityName;
	/**
	 * 始发地航站楼
	 */
	private String departTerm;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 目的地名称
	 */
	private String dstCityName;
	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;
	/**
	 * 航空公司
	 */
	private String airComp;
	/**
	 * 航空公司名称
	 */
	private String airCompanyName;
	/**
	 * 航空公司名称简称
	 */
	private String airCompanyShotName;
	/**
	 *航班 
	 */
	private String flightno;
	/**
	 * 机型
	 */
	private String planeType;
	/**
	 * 出发时间
	 */
	private String startTime;
	/**
	 * 到达时间
	 */
	private String endTime;
	/**
	 * 经停次数
	 */
	private String stopNumber;
	/**
	 * 餐食代码
	 */
	private String mealCode;
	/**
	 * 舱位信息
	 */
	private List<YeeXingCabinDTO> cabinList;
	
	
	
	public List<YeeXingCabinDTO> getCabinList() {
		return cabinList;
	}
	public void setCabinList(List<YeeXingCabinDTO> cabinList) {
		this.cabinList = cabinList;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getDepartTerm() {
		return departTerm;
	}
	public void setDepartTerm(String departTerm) {
		this.departTerm = departTerm;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getArrivalTerm() {
		return arrivalTerm;
	}
	public void setArrivalTerm(String arrivalTerm) {
		this.arrivalTerm = arrivalTerm;
	}
	public String getAirComp() {
		return airComp;
	}
	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getPlaneType() {
		return planeType;
	}
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
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
	public String getStopNumber() {
		return stopNumber;
	}
	public void setStopNumber(String stopNumber) {
		this.stopNumber = stopNumber;
	}
	public String getMealCode() {
		return mealCode;
	}
	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}
	public String getOrgCityName() {
		return orgCityName;
	}
	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}
	public String getDstCityName() {
		return dstCityName;
	}
	public void setDstCityName(String dstCityName) {
		this.dstCityName = dstCityName;
	}
	public String getAirCompanyName() {
		return airCompanyName;
	}
	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}
	public String getAirCompanyShotName() {
		return airCompanyShotName;
	}
	public void setAirCompanyShotName(String airCompanyShotName) {
		this.airCompanyShotName = airCompanyShotName;
	}
}
