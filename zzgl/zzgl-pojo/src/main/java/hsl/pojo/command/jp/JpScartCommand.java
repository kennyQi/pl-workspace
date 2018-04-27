package hsl.pojo.command.jp;

import hg.common.component.BaseCommand;

import java.util.Date;

@SuppressWarnings("serial")
public class JpScartCommand extends BaseCommand{
private String encryptString;

private String orgCity;

private String dstCity;

private String airComp;

private String from;

private String to;

private String airCompanyName;

private String flightno;

private String planeType;

private String startTime;

private String flyTime;

private String endTime;

private String orgCityName;

private String departTerm;

private String stopNumber;


private String dstCityName;

private String arrivalTerm;


private double buildFeeOrOilFee;

private double buildFee;

private double oilFee;

private String cabinNamecode;

private double totalPrice;

private double ibePrice;

private String cabinDiscount;

private Date startDate;

private Date endDate;

private String cabinCode;

private String stopNumbers;
/**
 * 查政策加密字符串
 */
private String flightencryptString;
/**
 * 单人支付总价(包括机建燃油)
 * singleTotalPrice + 价格政策　＋　手续费
 */
private Double singlePlatTotalPrice;

public String getEncryptString() {
	return encryptString;
}

public void setEncryptString(String encryptString) {
	this.encryptString = encryptString;
}

public String getOrgCity() {
	return orgCity;
}

public void setOrgCity(String orgCity) {
	this.orgCity = orgCity;
}

public String getDstCity() {
	return dstCity;
}

public void setDstCity(String dstCity) {
	this.dstCity = dstCity;
}

public String getAirComp() {
	return airComp;
}

public void setAirComp(String airComp) {
	this.airComp = airComp;
}

public String getFrom() {
	return from;
}

public void setFrom(String from) {
	this.from = from;
}

public String getTo() {
	return to;
}

public void setTo(String to) {
	this.to = to;
}

public String getAirCompanyName() {
	return airCompanyName;
}

public void setAirCompanyName(String airCompanyName) {
	this.airCompanyName = airCompanyName;
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

public String getOrgCityName() {
	return orgCityName;
}

public void setOrgCityName(String orgCityName) {
	this.orgCityName = orgCityName;
}

public String getDepartTerm() {
	return departTerm;
}

public void setDepartTerm(String departTerm) {
	this.departTerm = departTerm;
}

public String getStopNumber() {
	return stopNumber;
}

public void setStopNumber(String stopNumber) {
	this.stopNumber = stopNumber;
}

public String getFlyTime() {
	return flyTime;
}

public void setFlyTime(String flyTime) {
	this.flyTime = flyTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}

public String getDstCityName() {
	return dstCityName;
}

public void setDstCityName(String dstCityName) {
	this.dstCityName = dstCityName;
}

public String getArrivalTerm() {
	return arrivalTerm;
}

public void setArrivalTerm(String arrivalTerm) {
	this.arrivalTerm = arrivalTerm;
}

public double getBuildFeeOrOilFee() {
	return buildFeeOrOilFee;
}

public void setBuildFeeOrOilFee(double buildFeeOrOilFee) {
	this.buildFeeOrOilFee = buildFeeOrOilFee;
}

public double getBuildFee() {
	return buildFee;
}

public void setBuildFee(double buildFee) {
	this.buildFee = buildFee;
}

public double getOilFee() {
	return oilFee;
}

public void setOilFee(double oilFee) {
	this.oilFee = oilFee;
}

public String getCabinNamecode() {
	return cabinNamecode;
}

public void setCabinNamecode(String cabinNamecode) {
	this.cabinNamecode = cabinNamecode;
}

public double getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(double totalPrice) {
	this.totalPrice = totalPrice;
}

public double getIbePrice() {
	return ibePrice;
}

public void setIbePrice(double ibePrice) {
	this.ibePrice = ibePrice;
}

public String getCabinDiscount() {
	return cabinDiscount;
}

public void setCabinDiscount(String cabinDiscount) {
	this.cabinDiscount = cabinDiscount;
}


public Date getEndDate() {
	return endDate;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}


public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public String getCabinCode() {
	return cabinCode;
}

public void setCabinCode(String cabinCode) {
	this.cabinCode = cabinCode;
}

public String getStopNumbers() {
	return stopNumbers;
}

public void setStopNumbers(String stopNumbers) {
	this.stopNumbers = stopNumbers;
}

public Double getSinglePlatTotalPrice() {
	return singlePlatTotalPrice;
}

public void setSinglePlatTotalPrice(Double singlePlatTotalPrice) {
	this.singlePlatTotalPrice = singlePlatTotalPrice;
}

public String getFlightencryptString() {
	return flightencryptString;
}

public void setFlightencryptString(String flightencryptString) {
	this.flightencryptString = flightencryptString;
}


}
