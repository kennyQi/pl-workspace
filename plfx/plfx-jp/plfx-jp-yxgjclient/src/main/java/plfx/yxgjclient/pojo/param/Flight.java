package plfx.yxgjclient.pojo.param;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 航班信息
 * @author guotx
 *015-07-06
 */
@XStreamAlias("flight")
public class Flight {
	/**
	 * 承运方航空公司
	 */
	private String carriageAirline;
	/**
	 * 
	 */
	private String carriageFlight;
	/**
	 * 餐食代码
	 * 餐食标识 B:早餐； L：午餐； S：小吃
	 */
	private String mealCode;
	/**
	 * 舱位
	 */
	private String cabinCode;
	/**
	 * 剩余座位数
	 */
	private String cabinSales;
	/**
	 * Q值
	 */
	private String qCharge;
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
	 * 是否共享
	 */
	private String isShare;
	/**
	 * 定做舱位列表
	 */
	private String resBookDesigList;
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 始发地航站楼
	 */
	private String orgTerm;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 目的地航站楼
	 */
	private String dstTerm;
	/**
	 * 市场方航空公司
	 */
	private String marketingAirline;
	/**
	 * 出票方航司
	 */
	private String ticketingCarrier;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 承运方航班号
	 */
	private String carriageFlightno;
	/**
	 * Q值
	 */
	private String qcharge;
	/**
	 * 飞行时间
	 */
	private String duration;
	/**
	 * 经停数
	 */
	private String stopQuantity;
	/**
	 * 经停信息
	 */
	private List<StopOver>stopOvers;
	/**
	 * 舱位等级
	 */
	private String cabinClass;
	
	public String getQcharge() {
		return qcharge;
	}
	public void setQcharge(String qcharge) {
		this.qcharge = qcharge;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStopQuantity() {
		return stopQuantity;
	}
	public void setStopQuantity(String stopQuantity) {
		this.stopQuantity = stopQuantity;
	}
	public List<StopOver> getStopOvers() {
		return stopOvers;
	}
	public void setStopOvers(List<StopOver> stopOvers) {
		this.stopOvers = stopOvers;
	}
	public String getCabinClass() {
		return cabinClass;
	}
	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getOrgTerm() {
		return orgTerm;
	}
	public void setOrgTerm(String orgTerm) {
		this.orgTerm = orgTerm;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getDstTerm() {
		return dstTerm;
	}
	public void setDstTerm(String dstTerm) {
		this.dstTerm = dstTerm;
	}
	public String getMarketingAirline() {
		return marketingAirline;
	}
	public void setMarketingAirline(String marketingAirline) {
		this.marketingAirline = marketingAirline;
	}
	public String getTicketingCarrier() {
		return ticketingCarrier;
	}
	public void setTicketingCarrier(String ticketingCarrier) {
		this.ticketingCarrier = ticketingCarrier;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getCarriageFlight() {
		return carriageFlight;
	}
	public void setCarriageFlight(String carriageFlight) {
		this.carriageFlight = carriageFlight;
	}
	public String getMealCode() {
		return mealCode;
	}
	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}
	public String getCabinCode() {
		return cabinCode;
	}
	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}
	public String getCabinSales() {
		return cabinSales;
	}
	public void setCabinSales(String cabinSales) {
		this.cabinSales = cabinSales;
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
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public String getResBookDesigList() {
		return resBookDesigList;
	}
	public void setResBookDesigList(String resBookDesigList) {
		this.resBookDesigList = resBookDesigList;
	}
	public String getCarriageAirline() {
		return carriageAirline;
	}
	public void setCarriageAirline(String carriageAirline) {
		this.carriageAirline = carriageAirline;
	}
	public String getQCharge() {
		return qCharge;
	}
	public void setQCharge(String qCharge) {
		this.qCharge = qCharge;
	}
	public String getCarriageFlightno() {
		return carriageFlightno;
	}
	public void setCarriageFlightno(String carriageFlightno) {
		this.carriageFlightno = carriageFlightno;
	}

}
