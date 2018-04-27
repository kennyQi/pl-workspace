package pay.record.api.client.api.v1.pay.record.request.air;

import pay.record.api.client.common.ApiRequestBody;
import pay.record.api.client.common.api.PayRecordApiAction;

/**
 * 
 * @类功能说明：用户->经销商支付记录COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月12日下午4:09:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_UTJ_CREATE)
public class CreateAirUTJPayReocrdCommand extends ApiRequestBody{
	
	/**
	 * 经销商订单创建时间
	 */
	private String dealerOrderCreateDate;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 用户支付总价
	 */
	private Double userPayAmount;
	
	/**
	 * 用户支付的现金
	 */
	private Double userPayCash;
	
	/**
	 * 用户支付的优惠券金额
	 */
	private Double userPayCoupon;
	
	/**
	 * 用户支付的余额金额
	 */
	private Double userPayBalances;
	
	/**
	 * 客户付款账号
	 */
	private String userPayAccountNo;
	
	/**
	 * 客户业务流水号
	 */
	private String userBusinessSerialNumber;
	
	/**
	 * 航空公司
	 */
	private String airCompName;
	
	/**
	 * 航班号 string ZH9814
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
	
	/**
	 * 订单pnr
	 */
	private String pnr;

	public String getDealerOrderCreateDate() {
		return dealerOrderCreateDate;
	}

	public void setDealerOrderCreateDate(String dealerOrderCreateDate) {
		this.dealerOrderCreateDate = dealerOrderCreateDate;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Double getUserPayAmount() {
		return userPayAmount;
	}

	public void setUserPayAmount(Double userPayAmount) {
		this.userPayAmount = userPayAmount;
	}

	public Double getUserPayCash() {
		return userPayCash;
	}

	public void setUserPayCash(Double userPayCash) {
		this.userPayCash = userPayCash;
	}

	public Double getUserPayCoupon() {
		return userPayCoupon;
	}

	public void setUserPayCoupon(Double userPayCoupon) {
		this.userPayCoupon = userPayCoupon;
	}

	public Double getUserPayBalances() {
		return userPayBalances;
	}

	public void setUserPayBalances(Double userPayBalances) {
		this.userPayBalances = userPayBalances;
	}

	public String getUserPayAccountNo() {
		return userPayAccountNo;
	}

	public void setUserPayAccountNo(String userPayAccountNo) {
		this.userPayAccountNo = userPayAccountNo;
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
