package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class FlightGNDTO extends BaseDTO {
	/**
	 * 始发地,名称
	 */
	private String orgCityName;
	/**
	 * 目的地,名称
	 */
	private String dstCityName;
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 始发地航站楼
	 */
	private String departTerm;
	/**
	 * 航空公司名称 深圳航空
	 */
	private String airCompanyName;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;
	/**
	 * 航空公司
	 */
	private String airComp;
	/**
	 * 航班号
	 */
	private String flightno;
	/**
	 * 机型
	 */
	private String planeType;
	/**
	 * 出发时间
	 */
	private Date startTime;
	/**
	 * 到达时间
	 */
	private Date endTime;
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
	
	private List<CabinGNDTO> cabinList;
	/**
	 * 总飞行时间 120(单位分钟)
	 */
	private int flyTime;
	/**
	 * 价格xxx起
	 */
	private String minPic;
	
	public List<CabinGNDTO> getCabinList() {
		return cabinList;
	}

	public void setCabinList(List<CabinGNDTO> cabinList) {
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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
	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public int getFlyTime() {
		return flyTime;
	}

	public void setFlyTime(int flyTime) {
		this.flyTime = flyTime;
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

	public String getMinPic() {
		return minPic;
	}

	public void setMinPic(String minPic) {
		this.minPic = minPic;
	}
	
}
