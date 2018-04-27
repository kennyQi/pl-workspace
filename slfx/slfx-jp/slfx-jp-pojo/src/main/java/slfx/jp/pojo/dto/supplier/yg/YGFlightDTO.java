package slfx.jp.pojo.dto.supplier.yg;

import java.util.List;

import slfx.jp.pojo.dto.BaseJpDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightClassDTO;

/**
 * 
 * @类功能说明：易购航班查询信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:59:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGFlightDTO extends BaseJpDTO{
	/**
	 * 航班号 string ZH9814
	 */
	private String flightNo;
	/**
	 * 航空公司二字码
	 */
	private String carrier;
	/**
	 * 航空公司名称  深圳航空
	 */
	private String airCompName;
	/**
	 * 出发港口代码  SZX
	 */
	private String startPort;
	/**
	 * 目的港口代码  SHA
	 */
	private String endPort;
	/**
	 * 出发港口名称  上海虹桥
	 */
	private String startPortName;
	/**
	 * 目的港口名称  深圳宝安
	 */
	private String endPortName;
	/**
	 * 出发城市代码
	 */
	private String startCityCode;
	/**
	 * 目的城市代码
	 */
	private String endCityCode;
	/**
	 * 出发时间  hh:mm
	 */
	private String startTime;
	/**
	 *  到达时间  hh:mm
	 */
	private String endTime;
	/**
	 * 出发日期  2011/09/12 或 2011-09-12 接口不同格式会有差异
	 */ 
	private String startDate;
	/**
	 * 到达日期    2011/09/12 或 2011-09-12 接口不同格式会有差异
	 */
	private String endDate;
	/**
	 * 总飞行时间  120(单位分钟)
	 */
	private Integer flyTime;
	/**
	 * 机型代码  747
	 */
	private String aircraftCode;
	/**
	 * 机型名称 波音737
	 */
	private String aircraftName;
	/**
	 * 燃油费 
	 */
	private Double fuelSurTax;
	/**
	 * 机建费 
	 */
	private Double airportTax;
	/**
	 * 票面价 
	 *
	 */
	private Double fare;
	/**
	 * 税费(机建费+燃油费)
	 */
	private Double taxAmount;
	/**
	 * 共享航班号
	 */
	private String shareFlightNum;
	/**
	 * 经停次数
	 */
	private Integer stopNum;
	/**
	 * 出发航站楼
	 */
	private String startTerminal;
	/**
	 * 到达航站楼
	 */
	private String endTerminal;
	/**
	 * 有无餐食
	 */
	private Boolean isFood;
	/**
	 *	Y舱价格 
	 */
	private Double YPrice;
	
	/**
	 * 里程
	 */
	private Integer mileage;
	/**
	 * 舱位代码
	 */
	private String classCode;
	
	
	/**
	 * 航程中最低价舱位   注：该对象只有票面价和舱位类型
	 */
	private SlfxFlightClassDTO cheapestFlightClass;

	private List<SlfxFlightClassDTO> flightClassList;


	public String getFlightNo() {
		return flightNo;
	}


	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public String getCarrier() {
		return carrier;
	}


	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public String getAirCompName() {
		return airCompName;
	}


	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}


	public String getStartPort() {
		return startPort;
	}


	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}


	public String getEndPort() {
		return endPort;
	}


	public void setEndPort(String endPort) {
		this.endPort = endPort;
	}


	public String getStartPortName() {
		return startPortName;
	}


	public void setStartPortName(String startPortName) {
		this.startPortName = startPortName;
	}


	public String getEndPortName() {
		return endPortName;
	}


	public void setEndPortName(String endPortName) {
		this.endPortName = endPortName;
	}


	public String getStartCityCode() {
		return startCityCode;
	}


	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}


	public String getEndCityCode() {
		return endCityCode;
	}


	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
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


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public Integer getFlyTime() {
		return flyTime;
	}


	public void setFlyTime(Integer flyTime) {
		this.flyTime = flyTime;
	}


	public String getAircraftCode() {
		return aircraftCode;
	}


	public void setAircraftCode(String aircraftCode) {
		this.aircraftCode = aircraftCode;
	}


	public String getAircraftName() {
		return aircraftName;
	}


	public void setAircraftName(String aircraftName) {
		this.aircraftName = aircraftName;
	}


	public Double getFuelSurTax() {
		return fuelSurTax;
	}


	public void setFuelSurTax(Double fuelSurTax) {
		this.fuelSurTax = fuelSurTax;
	}


	public Double getAirportTax() {
		return airportTax;
	}


	public void setAirportTax(Double airportTax) {
		this.airportTax = airportTax;
	}


	public Double getFare() {
		return fare;
	}


	public void setFare(Double fare) {
		this.fare = fare;
	}


	public Double getTaxAmount() {
		return taxAmount;
	}


	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}


	public String getShareFlightNum() {
		return shareFlightNum;
	}


	public void setShareFlightNum(String shareFlightNum) {
		this.shareFlightNum = shareFlightNum;
	}


	public Integer getStopNum() {
		return stopNum;
	}


	public void setStopNum(Integer stopNum) {
		this.stopNum = stopNum;
	}


	public String getStartTerminal() {
		return startTerminal;
	}


	public void setStartTerminal(String startTerminal) {
		this.startTerminal = startTerminal;
	}


	public String getEndTerminal() {
		return endTerminal;
	}


	public void setEndTerminal(String endTerminal) {
		this.endTerminal = endTerminal;
	}


	public Boolean getIsFood() {
		return isFood;
	}


	public void setIsFood(Boolean isFood) {
		this.isFood = isFood;
	}


	public Double getYPrice() {
		return YPrice;
	}


	public void setYPrice(Double yPrice) {
		YPrice = yPrice;
	}


	public Integer getMileage() {
		return mileage;
	}


	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}


	public String getClassCode() {
		return classCode;
	}


	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}


	public SlfxFlightClassDTO getCheapestFlightClass() {
		return cheapestFlightClass;
	}


	public void setCheapestFlightClass(SlfxFlightClassDTO cheapestFlightClass) {
		this.cheapestFlightClass = cheapestFlightClass;
	}


	public List<SlfxFlightClassDTO> getFlightClassList() {
		return flightClassList;
	}


	public void setFlightClassList(List<SlfxFlightClassDTO> flightClassList) {
		this.flightClassList = flightClassList;
	}
	

	
}
