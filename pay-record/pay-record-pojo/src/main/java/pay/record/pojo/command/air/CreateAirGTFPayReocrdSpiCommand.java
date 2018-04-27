package pay.record.pojo.command.air;

import pay.record.pojo.command.BasePayRecordCommand;

/**
 * 
 * @类功能说明：供应商->分销机票支付记录SpiCommand
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2016年1月12日下午4:09:23
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreateAirGTFPayReocrdSpiCommand extends BasePayRecordCommand{
	/**
	 * 供应商退款金额
	 */
	private Double supplierRefundPrice;
	
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

	public Double getSupplierRefundPrice() {
		return supplierRefundPrice;
	}

	public void setSupplierRefundPrice(Double supplierRefundPrice) {
		this.supplierRefundPrice = supplierRefundPrice;
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
