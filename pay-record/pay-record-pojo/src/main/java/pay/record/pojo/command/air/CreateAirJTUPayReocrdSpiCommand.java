package pay.record.pojo.command.air;

import java.util.Date;

import pay.record.pojo.command.BasePayRecordCommand;

/**
 * 
 * @类功能说明：经销商->用户机票支付记录SpiCommand
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月12日下午4:09:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreateAirJTUPayReocrdSpiCommand extends BasePayRecordCommand{

	/**
	 * 经销商订单创建时间
	 */
	private Date dealerOrderCreateDate;
	
	/**
	 * 平台订单号
	 */
	private String platOrderNo;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 客户收款账号
	 */
	private String userPayAccountNo;
	
	/**
	 * 退款给用户金额
	 */
	private Double dealerRefundPrice;
	
	/**
	 * 客户业务流水号
	 */
	private String userBusinessSerialNumber;
	
	/**
	 * 航空公司
	 */
	private String airCompName;
	
	/**
	 * 航班号
	 */
	private String flightNo;
	
	/**
	 * 舱位代码
	 */
	private String classCode;
	
	/**
	 * 舱位名称
	 */
	private String cabinName;
	
	private String pnr;

	public Date getDealerOrderCreateDate() {
		return dealerOrderCreateDate;
	}

	public void setDealerOrderCreateDate(Date dealerOrderCreateDate) {
		this.dealerOrderCreateDate = dealerOrderCreateDate;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public String getUserPayAccountNo() {
		return userPayAccountNo;
	}

	public void setUserPayAccountNo(String userPayAccountNo) {
		this.userPayAccountNo = userPayAccountNo;
	}

	public Double getDealerRefundPrice() {
		return dealerRefundPrice;
	}

	public void setDealerRefundPrice(Double dealerRefundPrice) {
		this.dealerRefundPrice = dealerRefundPrice;
	}

	public String getUserBusinessSerialNumber() {
		return userBusinessSerialNumber;
	}

	public void setUserBusinessSerialNumber(String userBusinessSerialNumber) {
		this.userBusinessSerialNumber = userBusinessSerialNumber;
	}

	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
}
