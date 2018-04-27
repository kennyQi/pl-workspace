package pay.record.api.client.api.v1.pay.record.request.air;

import java.util.Date;

import pay.record.api.client.common.ApiRequestBody;
import pay.record.api.client.common.api.PayRecordApiAction;

/**
 * 
 * @类功能说明：供应商->分销支付记录COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月12日下午4:09:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@PayRecordApiAction(PayRecordApiAction.PAY_RECORD_AIR_GTF_CREATE)
public class CreateAirGTFPayReocrdCommand extends ApiRequestBody{
	
	/**
	 * 平台订单创建时间
	 */
	private Date platOrderCreateDate;
	
	/**
	 * 供应商订单号
	 */
	private String supplierOrderNo;
	
	/**
	 * 平台订单号
	 */
	private String platOrderNo;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	
	/**
	 * 供应商退款金额
	 */
	private Double supplierRefundPrice;
	
	/**
	 * 供应商业务流水号
	 */
	private String supplierBusinessSerialNumber;
	
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
	
	/**
	 * 返点/折扣
	 */
	private Double rebate;
	
	/**
	 * 佣金
	 */
	private Double brokerage;

	public Date getPlatOrderCreateDate() {
		return platOrderCreateDate;
	}

	public void setPlatOrderCreateDate(Date platOrderCreateDate) {
		this.platOrderCreateDate = platOrderCreateDate;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
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

	public Double getSupplierRefundPrice() {
		return supplierRefundPrice;
	}

	public void setSupplierRefundPrice(Double supplierRefundPrice) {
		this.supplierRefundPrice = supplierRefundPrice;
	}

	public String getSupplierBusinessSerialNumber() {
		return supplierBusinessSerialNumber;
	}

	public void setSupplierBusinessSerialNumber(String supplierBusinessSerialNumber) {
		this.supplierBusinessSerialNumber = supplierBusinessSerialNumber;
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

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(Double brokerage) {
		this.brokerage = brokerage;
	}
}
