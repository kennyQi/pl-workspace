package slfx.jp.command.client;

import java.util.List;

/**
 * 
 * @类功能说明：ABE下单Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:04:56
 * @版本：V1.0
 *
 */
public class ABEOrderFlightCommand {
	
	/**
	 * 航空公司代码
	 * 必填
	 */
	private String carrier;
	
	/**
	 * 航班号 如：MU1234
	 * 必填
	 */
	private String flightNo;
	
	/**
	 * 出发城市代码   必填
	 */
	private String startCityCode;
	
	/**
	 * 目的城市代码   必填
	 */
	private String endCityCode;
	
	/**
	 * 出发日期   必填
	 */
	private String startDate;
	
	/**
	 * 出发时间  格式：07:30 代表7点30
	 * 必填
	 */
	private String startTime;
	
	/**
	 * 到达日期    必填
	 */
	private String endDate;
	
	/**
	 * 到达时间   必填
	 */
	private String endTime;
	
	/**
	 * 机型代码   必填
	 */
	private String aircraftCode;
	
	/**
	 *  舱位码   必填
	 */
	private String classCode;
	
	/**
	 *  舱位价格   必填
	 */
	private Double classPrice;
	
	/**
	 *  舱位折扣  必填
	 */
	private String classRebate;
	
	/**
	 * Y舱价格   必填
	 */
	private Double YPrice;
	
	/**
	 * 燃油费   必填
	 */
	private Double fuelSurTax;
	
	/**
	 * 机场建设费   必填
	 */
	private Double airportTax;
	
	/**
	 * 里程   必填
	 */
	private Integer mileage;
	
	/**
	 * 旅客信息集合   必填
	 * 注意：
	 * 		1.婴儿的旅客序号为0,可以重复， 其他旅客序号不能重复
	 *		2.婴儿人数必须成人一对一，并且在list中的顺序为一个成人后面跟一个婴儿
	 *		例如：3个成人，2个婴儿  旅客序号的顺序为：1,0,2,0,3 
	 */
	private List<ABEPassengerCommand> abePsgList;
	
	/**
	 * 乘客类型报价集合   必填
	 * 仅传旅客信息集合里有的乘客类型   必填
	 */
	private List<ABEPriceDetailCommand> abePriceDetailList;
	
	/**
	 * ABE订单信息描述 
	 */
	private ABEOrderInfoCommand abeOrderInfoCommand;
	
	/**
	 * ABE订单联系信息
	 */
	private ABELinkerInfoCommand abeLinkerInfoCommand;

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAircraftCode() {
		return aircraftCode;
	}

	public void setAircraftCode(String aircraftCode) {
		this.aircraftCode = aircraftCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public Double getClassPrice() {
		return classPrice;
	}

	public void setClassPrice(Double classPrice) {
		this.classPrice = classPrice;
	}

	public String getClassRebate() {
		return classRebate;
	}

	public void setClassRebate(String classRebate) {
		this.classRebate = classRebate;
	}

	public Double getYPrice() {
		return YPrice;
	}

	public void setYPrice(Double yPrice) {
		YPrice = yPrice;
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

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public List<ABEPassengerCommand> getAbePsgList() {
		return abePsgList;
	}

	public void setAbePsgList(List<ABEPassengerCommand> abePsgList) {
		this.abePsgList = abePsgList;
	}

	public List<ABEPriceDetailCommand> getAbePriceDetailList() {
		return abePriceDetailList;
	}

	public void setAbePriceDetailList(List<ABEPriceDetailCommand> abePriceDetailList) {
		this.abePriceDetailList = abePriceDetailList;
	}

	public ABEOrderInfoCommand getAbeOrderInfoCommand() {
		return abeOrderInfoCommand;
	}

	public void setAbeOrderInfoCommand(ABEOrderInfoCommand abeOrderInfoCommand) {
		this.abeOrderInfoCommand = abeOrderInfoCommand;
	}

	public ABELinkerInfoCommand getAbeLinkerInfoCommand() {
		return abeLinkerInfoCommand;
	}

	public void setAbeLinkerInfoCommand(ABELinkerInfoCommand abeLinkerInfoCommand) {
		this.abeLinkerInfoCommand = abeLinkerInfoCommand;
	}

}
