package pay.record.domain.model.pay;

import java.util.Date;

import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pay.record.domain.base.M;
import pay.record.pojo.command.air.CreateAirFTGPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirGTFPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirJTUPayReocrdSpiCommand;
import pay.record.pojo.command.air.CreateAirUTJPayReocrdSpiCommand;

/**
 * 
 * @类功能说明：机票支付记录
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月26日下午4:59:21
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_PAYRECORD + "_AIR")
public class AirPayRecord extends PayRecordBaseModel{
	/**
	 * 航空公司
	 */
	@Column(name = "AIR_COMP_NAME",length = 64)
	private String airCompName;
	
	/**
	 * 航班号 string ZH9814
	 */
	@Column(name = "FLIGHT_NO", length = 8)
	private String flightNo;
	
	/** 
	 * 舱位代码
	 */
	@Column(name = "CLASS_CODE", length = 6)
	private String classCode;
	
	/**
	 * 舱位名称
	 */
	@Column(name = "CABIN_NAME", length = 10)
	private String cabinName;
	
	/**
	 * 订单pnr
	 */
	@Column(name = "PNR", length = 8)
	private String pnr;
	
	/**
	 * 返点/折扣
	 */
	@Column(name = "REBATE", columnDefinition = M.MONEY_COLUM)
	private Double rebate;
	
	/**
	 * 佣金
	 */
	@Column(name = "BROKERAGE", columnDefinition = M.MONEY_COLUM)
	private Double brokerage;
	
	/**
	 * 平台付供应商总价
	 */
	@Column(name = "PALT_PAYSUPPLIERAMOUNT", columnDefinition = M.MONEY_COLUM)
	private Double paltPaysupplierAmount;
	
	/**
	 * 经销商实退金额
	 */
	@Column(name = "SUPPLIER_REFUND_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double supplierRefundPrice;
	
	/**
	 * 退款给用户金额
	 */
	@Column(name = "DEALER_REFUND_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double dealerRefundPrice;
	
	public AirPayRecord(){
		super();
	}
	
	/**
	 * 新增机票支付记录  用户->经销商
	 * @param spiCommand
	 */
	public AirPayRecord(CreateAirUTJPayReocrdSpiCommand spiCommand) {
		this.setId(UUIDGenerator.getUUID());
		this.setFromProjectIP(spiCommand.getFromProjectIP());
		this.setFromProjectCode(spiCommand.getFromProjectCode());
		this.setFromProjectEnv(spiCommand.getFromProjectEnv());
		this.setBooker(spiCommand.getBooker());
		this.setPassengers(spiCommand.getPassengers());
		this.setStartPlace(spiCommand.getStartPlace());
		this.setDestination(spiCommand.getDestination());
		this.setOrderStatus(spiCommand.getOrderStatus());
		this.setPayStatus(spiCommand.getPayStatus());
		this.setPayPlatform(spiCommand.getPayPlatform());
		this.setPayTime(spiCommand.getPayTime());
		this.setRemarks(spiCommand.getRemarks());
		this.setFromClientType(spiCommand.getFromClientType());
		this.setRecordType(spiCommand.getRecordType());
		this.setCreateDate(new Date());
		
		this.setDealerOrderCreateDate(spiCommand.getDealerOrderCreateDate());
		this.setDealerOrderNo(spiCommand.getDealerOrderNo());
		this.setUserPayAmount(spiCommand.getUserPayAmount());
		this.setUserPayCash(spiCommand.getUserPayCash());
		this.setUserPayCoupon(spiCommand.getUserPayCoupon());
		this.setUserPayBalances(spiCommand.getUserPayBalances());
		this.setUserPayAccountNo(spiCommand.getUserPayAccountNo());
		this.setUserBusinessSerialNumber(spiCommand.getUserBusinessSerialNumber());
		this.setAirCompName(spiCommand.getAirCompName());
		this.setFlightNo(spiCommand.getFlightNo());
		this.setClassCode(spiCommand.getClassCode());
		this.setCabinName(spiCommand.getCabinName());
		this.setPnr(spiCommand.getPnr());
	}

	/**
	 * 新增机票支付记录  分销->供应商
	 * @param spiCommand
	 */
	public AirPayRecord(CreateAirFTGPayReocrdSpiCommand spiCommand) {
		this.setId(UUIDGenerator.getUUID());
		this.setFromProjectIP(spiCommand.getFromProjectIP());
		this.setFromProjectCode(spiCommand.getFromProjectCode());
		this.setFromProjectEnv(spiCommand.getFromProjectEnv());
		this.setBooker(spiCommand.getBooker());
		this.setPassengers(spiCommand.getPassengers());
		this.setStartPlace(spiCommand.getStartPlace());
		this.setDestination(spiCommand.getDestination());
		this.setOrderStatus(spiCommand.getOrderStatus());
		this.setPayStatus(spiCommand.getPayStatus());
		this.setPayPlatform(spiCommand.getPayPlatform());
		this.setPayTime(spiCommand.getPayTime());
		this.setRemarks(spiCommand.getRemarks());
		this.setFromClientType(spiCommand.getFromClientType());
		this.setRecordType(spiCommand.getRecordType());
		this.setCreateDate(new Date());
		
		this.setPlatOrderCreateDate(spiCommand.getPlatOrderCreateDate());
		this.setSupplierOrderNo(spiCommand.getSupplierOrderNo());
		this.setPlatOrderNo(spiCommand.getPlatOrderNo());
		this.setDealerOrderNo(spiCommand.getDealerOrderNo());
		this.setPaltPaysupplierAmount(spiCommand.getPaltPaysupplierAmount());
		this.setSupplierBusinessSerialNumber(spiCommand.getSupplierBusinessSerialNumber());
		this.setAirCompName(spiCommand.getAirCompName());
		this.setFlightNo(spiCommand.getFlightNo());
		this.setClassCode(spiCommand.getClassCode());
		this.setCabinName(spiCommand.getCabinName());
		this.setPnr(spiCommand.getPnr());
		this.setRebate(spiCommand.getRebate());
		this.setBrokerage(spiCommand.getBrokerage());
	}

	/**
	 * 新增机票支付记录  供应商->分销
	 * @param spiCommand
	 */
	public AirPayRecord(CreateAirGTFPayReocrdSpiCommand spiCommand) {
		this.setId(UUIDGenerator.getUUID());
		this.setFromProjectIP(spiCommand.getFromProjectIP());
		this.setFromProjectCode(spiCommand.getFromProjectCode());
		this.setFromProjectEnv(spiCommand.getFromProjectEnv());
		this.setBooker(spiCommand.getBooker());
		this.setPassengers(spiCommand.getPassengers());
		this.setStartPlace(spiCommand.getStartPlace());
		this.setDestination(spiCommand.getDestination());
		this.setOrderStatus(spiCommand.getOrderStatus());
		this.setPayStatus(spiCommand.getPayStatus());
		this.setPayPlatform(spiCommand.getPayPlatform());
		this.setPayTime(spiCommand.getPayTime());
		this.setRemarks(spiCommand.getRemarks());
		this.setFromClientType(spiCommand.getFromClientType());
		this.setCreateDate(new Date());
		
		this.setPlatOrderCreateDate(spiCommand.getPlatOrderCreateDate());
		this.setSupplierOrderNo(spiCommand.getSupplierOrderNo());
		this.setPlatOrderNo(spiCommand.getPlatOrderNo());
		this.setDealerOrderNo(spiCommand.getDealerOrderNo());
		this.setSupplierRefundPrice(spiCommand.getSupplierRefundPrice());
		this.setSupplierBusinessSerialNumber(spiCommand.getSupplierBusinessSerialNumber());
		this.setAirCompName(spiCommand.getAirCompName());
		this.setFlightNo(spiCommand.getFlightNo());
		this.setClassCode(spiCommand.getClassCode());
		this.setCabinName(spiCommand.getCabinName());
		this.setPnr(spiCommand.getPnr());
		this.setRebate(spiCommand.getRebate());
		this.setBrokerage(spiCommand.getBrokerage());
	}

	/**
	 * 新增机票支付记录  经销商->用户
	 * @param spiCommand
	 */
	public AirPayRecord(CreateAirJTUPayReocrdSpiCommand spiCommand) {
		this.setId(UUIDGenerator.getUUID());
		this.setFromProjectIP(spiCommand.getFromProjectIP());
		this.setFromProjectCode(spiCommand.getFromProjectCode());
		this.setFromProjectEnv(spiCommand.getFromProjectEnv());
		this.setBooker(spiCommand.getBooker());
		this.setPassengers(spiCommand.getPassengers());
		this.setStartPlace(spiCommand.getStartPlace());
		this.setDestination(spiCommand.getDestination());
		this.setOrderStatus(spiCommand.getOrderStatus());
		this.setPayStatus(spiCommand.getPayStatus());
		this.setPayPlatform(spiCommand.getPayPlatform());
		this.setPayTime(spiCommand.getPayTime());
		this.setRemarks(spiCommand.getRemarks());
		this.setFromClientType(spiCommand.getFromClientType());
		this.setCreateDate(new Date());
		
		this.setDealerOrderCreateDate(spiCommand.getDealerOrderCreateDate());
		this.setPlatOrderNo(spiCommand.getPlatOrderNo());
		this.setDealerOrderNo(spiCommand.getDealerOrderNo());
		this.setUserPayAccountNo(spiCommand.getUserPayAccountNo());
		this.setDealerRefundPrice(spiCommand.getDealerRefundPrice());
		this.setUserBusinessSerialNumber(spiCommand.getUserBusinessSerialNumber());
		this.setAirCompName(spiCommand.getAirCompName());
		this.setFlightNo(spiCommand.getFlightNo());
		this.setClassCode(spiCommand.getClassCode());
		this.setCabinName(spiCommand.getCabinName());
		this.setPnr(spiCommand.getPnr());
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
	
	public Double getPaltPaysupplierAmount() {
		return paltPaysupplierAmount;
	}

	public void setPaltPaysupplierAmount(Double paltPaysupplierAmount) {
		this.paltPaysupplierAmount = paltPaysupplierAmount;
	}

	public Double getSupplierRefundPrice() {
		return supplierRefundPrice;
	}

	public void setSupplierRefundPrice(Double supplierRefundPrice) {
		this.supplierRefundPrice = supplierRefundPrice;
	}

	public Double getDealerRefundPrice() {
		return dealerRefundPrice;
	}

	public void setDealerRefundPrice(Double dealerRefundPrice) {
		this.dealerRefundPrice = dealerRefundPrice;
	}
}
