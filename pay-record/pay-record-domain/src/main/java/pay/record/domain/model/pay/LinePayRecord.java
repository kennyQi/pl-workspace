package pay.record.domain.model.pay;

import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pay.record.domain.base.M;
import pay.record.pojo.command.line.CreateLineUTJPayReocrdSpiCommand;

/**
 * 
 * @类功能说明：线路支付记录
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月26日下午5:01:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_PAYRECORD + "_LINE")
public class LinePayRecord extends PayRecordBaseModel{
	/**
	 * 线路id
	 */
	@Column(name="LINE_ID", length=64)
	private String lineID;
	
	/**
	 * 线路名称
	 */
	@Column(name="LINE_NAME", length=64)
	private String lineName;
	
	/**
	 * 线路类别
	 */
	@Column(name="LINE_TYPE", length=64)
	private String type;
	
	/**
	 * 成人人数
	 */
	@Column(name = "ADULT_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer adultNo;
	
	/**
	 * 儿童人数
	 */
	@Column(name = "CHILD_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer childNo;
	
	/**
	 * 成人销售单价
	 */
	@Column(name = "ADULT_UNIT_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double adultUnitPrice;
	
	/**
	 * 儿童销售单价
	 */
	@Column(name = "CHILD_UNIT_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double childUnitPrice;
	
	/**
	 * 销售总价
	 */
	@Column(name = "SALE_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double salePrice;
	
	/**
	 * 销售定金
	 */
	@Column(name = "BARGAIN_MONEY", columnDefinition = M.MONEY_COLUM)
	private Double bargainMoney;
	
	/**
	 * 发团日期
	 */
	@Column(name = "TRAVEL_DATE", columnDefinition = M.DATE_COLUM)
	private Date travelDate;
	
	public LinePayRecord(){
		super();
	}

	public LinePayRecord(CreateLineUTJPayReocrdSpiCommand spiCommand) {
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
		
		this.setLineID(spiCommand.getLineID());
		this.setLineName(spiCommand.getLineName());
		this.setType(spiCommand.getType());
		this.setAdultNo(spiCommand.getAdultNo());
		this.setChildNo(spiCommand.getChildNo());
		this.setAdultUnitPrice(spiCommand.getAdultUnitPrice());
		this.setChildUnitPrice(spiCommand.getChildUnitPrice());
		this.setSalePrice(spiCommand.getSalePrice());
		this.setBargainMoney(spiCommand.getBargainMoney());
		this.setTravelDate(spiCommand.getTravelDate());
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAdultNo() {
		return adultNo;
	}

	public void setAdultNo(Integer adultNo) {
		this.adultNo = adultNo;
	}

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(Double bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
	
//	public LinePayRecord(CreateLineUTJPayReocrdSpiCommand spiCommand){
//		this.setId(UUIDGenerator.getUUID());
//		this.setFromProjectCode(spiCommand.getFromProjectCode());
//		this.setFromProjectIP(spiCommand.getFromProjectIP());
//		this.setBooker(spiCommand.getBooker());
//		this.setPlatOrderNo(spiCommand.getPlatOrderNo());
//		this.setDealerOrderNo(spiCommand.getDealerOrderNo());
//		this.setSupplierNo(spiCommand.getSupplierNo());
//		this.setPaySerialNumber(spiCommand.getPaySerialNumber());
//		this.setStartCity(spiCommand.getStartCity());
//		this.setDestCity(spiCommand.getDestCity());
//		this.setIncomeMoney(spiCommand.getIncomeMoney());
//		this.setPayMoney(spiCommand.getPayMoney());
//		this.setRebate(spiCommand.getRebate());
//		this.setBrokerage(spiCommand.getBrokerage());
//		this.setOrderStatus(spiCommand.getOrderStatus());
//		this.setPayStatus(spiCommand.getPayStatus());
//		this.setPayTime(spiCommand.getPayTime());
//		this.setRecordType(spiCommand.getRecordType());
//		this.setCreateTime(new Date());
//		this.setRemarks(spiCommand.getRemarks());
//		this.setPayPlatform(spiCommand.getPayPlatform());
//		this.setPayAccountNo(spiCommand.getPayAccountNo());
//		this.setPassengers(spiCommand.getPassengers());
//		this.setLineID(spiCommand.getLineID());
//		this.setLineName(spiCommand.getLineName());
//		this.setType(spiCommand.getType());
//		this.setAdultNo(spiCommand.getAdultNo());
//		this.setChildNo(spiCommand.getChildNo());
//		this.setAdultUnitPrice(spiCommand.getAdultUnitPrice());
//		this.setChildUnitPrice(spiCommand.getChildUnitPrice());
//		this.setSalePrice(spiCommand.getSalePrice());
//		this.setBargainMoney(spiCommand.getBargainMoney());
//		this.setTravelDate(spiCommand.getTravelDate());
//		this.setFromClientType(spiCommand.getFromClientType());
//		this.setOrderPrice(spiCommand.getOrderPrice());
//		this.setRealRefundMoney(spiCommand.getRealRefundMoney());
//		this.setToUserRefundMoney(spiCommand.getToUserRefundMoney());
//	}

	/***
	 * 
	 * @方法功能说明：修改线路支付记录
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年12月14日上午8:25:44
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
//	public void changeAirPayRecord(ModifyLinePayReocrdSpiCommand command) {
//		this.setFromProjectCode(command.getFromProjectCode());
//		this.setFromProjectIP(command.getFromProjectIP());
//		this.setBooker(command.getBooker());
//		this.setPlatOrderNo(command.getPlatOrderNo());
//		this.setDealerOrderNo(command.getDealerOrderNo());
//		this.setSupplierNo(command.getSupplierNo());
//		this.setPaySerialNumber(command.getPaySerialNumber());
//		this.setStartCity(command.getStartCity());
//		this.setDestCity(command.getDestCity());
//		this.setIncomeMoney(command.getIncomeMoney());
//		this.setPayMoney(command.getPayMoney());
//		this.setRebate(command.getRebate());
//		this.setBrokerage(command.getBrokerage());
//		this.setOrderStatus(command.getOrderStatus());
//		this.setPayStatus(command.getPayStatus());
//		this.setPayTime(command.getPayTime());
//		this.setRecordType(command.getRecordType());
//		this.setCreateTime(new Date());
//		this.setRemarks(command.getRemarks());
//		this.setPayPlatform(command.getPayPlatform());
//		this.setPayAccountNo(command.getPayAccountNo());
//		this.setPassengers(command.getPassengers());
//		this.setLineID(command.getLineID());
//		this.setLineName(command.getLineName());
//		this.setType(command.getType());
//		this.setAdultNo(command.getAdultNo());
//		this.setChildNo(command.getChildNo());
//		this.setAdultUnitPrice(command.getAdultUnitPrice());
//		this.setChildUnitPrice(command.getChildUnitPrice());
//		this.setSalePrice(command.getSalePrice());
//		this.setBargainMoney(command.getBargainMoney());
//		this.setTravelDate(command.getTravelDate());
//		this.setFromClientType(command.getFromClientType());
//		this.setOrderPrice(command.getOrderPrice());
//		this.setRealRefundMoney(command.getRealRefundMoney());
//		this.setToUserRefundMoney(command.getToUserRefundMoney());
//		
//	}
	
}
