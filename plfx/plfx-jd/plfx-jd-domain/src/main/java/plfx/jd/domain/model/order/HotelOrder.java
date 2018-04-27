package plfx.jd.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import plfx.jd.domain.model.M;
import plfx.jd.domain.model.crm.HotelDealer;
import plfx.jd.domain.model.crm.HotelSupplier;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCreateCommand;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateCommandDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;


/**
 * 
 * @类功能说明：酒店订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年01月27日下午2:24:28
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "ORDER")
public class HotelOrder extends BaseModel {
	/**
	 * 经销商订单遍号
	 */
	@Column(name = "DEALER_ORDER_NO", length = 64)
	private String dealerOrderNo;
	/**
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 64)
	private String orderNo;
	/**
	 * 供应商平台订单号
	 */
	@Column(name = "SUPPLIER_ORDER_NO", length = 64)
	private String supplierOrderNo;
	
	/**
	 * 供应商平台
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_PROJECT_ID")
	private HotelSupplier supplier;
	
	/**
	 * 经销商平台
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEALER_PROJECT_ID")
	private HotelDealer dealer;
	
	/**
	 * 订单状态
	 */
	@Column(name = "STATUS", length = 32)
	private String status;
	/**
	 * 对用户展示的订单状态
	 */
	@Column(name = "SHOW_STATUS",columnDefinition=M.LONG_NUM_COLUM)
	private Long showStatus;
	/**
	 * 订单产生的罚金
	 */
	@Column(name = "PENALTY_TO_CUSTOMER",columnDefinition = M.MONEY_COLUM)
	private BigDecimal penaltyToCustomer;
	/**
	 * 罚金货币类型
	 */
	@Column(name = "PENALTY_CURRENCY_CODE", length = 32)
	private String penaltyCurrencyCode;
	/**
	 * 最晚取消时间
	 */
	@Column(name = "CANCEL_TIME",columnDefinition = M.DATE_COLUM)
	private Date cancelTime;
	/**
	 * 是否要发票
	 */
	@Column(name = "HAS_INVOICE",columnDefinition = M.CHAR_COLUM)
	private boolean hasInvoice;
	/**
	 * 发票模型
	 */
	@Column(name = "INVOICE_MODE", length = 64)
	private String invoiceMode;
	/**
	 * 发票
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "INVOICE_ID")
	private Invoice invoice;
	/**
	 * 担保规则
	 */
	@Column(name = "GUARANTEE_RULE", columnDefinition = M.TEXT_COLUM)
	private String guaranteeRule;
	/**
	 * 预付规则
	 */
	@Column(name = "PREPAY_RULE",columnDefinition = M.TEXT_COLUM)
	private String prepayRule;
	/**
	 * 酒店名称
	 */
	@Column(name = "HOTEL_NAME", length = 64)
	private String hotelName;
	/**
	 * 酒店编号
	 */
	@Column(name = "HOTEL_NO", length = 64)
	private String hotelNo;
	/**
	 * 房间名称
	 */
	@Column(name = "ROOM_NAME", length = 64)
	private String roomName;
	/**
	 * 产品名称
	 */
	@Column(name = "RATE_PLAN_NAME", length = 64)
	private String ratePlanName;
	/**
	 * 当前是否可以取消
	 */
	@Column(name = "IS_CANCELABLE",columnDefinition = M.CHAR_COLUM)
	private Boolean isCancelable;
	/**
	 * 预约日期
	 */
	@Column(name = "CREATION_DATE",columnDefinition = M.DATE_COLUM)
	private Date creationDate;
	/**
	 * 优惠
	 */
	@Column(name = "COUPON",columnDefinition = M.MONEY_COLUM)
	private BigDecimal coupon;
	/**
	 * 入住日期
	 */
	@Column(name = "ARRIVAL_DATE",columnDefinition = M.DATE_COLUM)
	private Date arrivalDate;
	/**
	 * 离店日期
	 */
	@Column(name = "DEPARTURE_DATE",columnDefinition = M.DATE_COLUM)
	private Date departureDate;
	/**
	 * 房间数量
	 */
	@Column(name = "NUMBER_OF_ROOMS",columnDefinition = M.NUM_COLUM)
	private int numberOfRooms;
	/**
	 * 客人数量
	 */
	@Column(name = "NUMBER_OF_CUSTOMERS",columnDefinition = M.NUM_COLUM)
	private int numberOfCustomers;
	/**
	 * 住客
	 */
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "hotelOrder")
	private List<Customer> customers;
	/**
	 * 总价
	 */
	@Column(name = "TOTAL_PRICE",columnDefinition = M.MONEY_COLUM)
	private Double totalPrice;
	
	
	
	
	public String getHotelNo() {
		return hotelNo;
	}
	public void setHotelNo(String hotelNo) {
		this.hotelNo = hotelNo;
	}
	public String getDealerOrderNo() {
		return dealerOrderNo;
	}
	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}
	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(Long showStatus) {
		this.showStatus = showStatus;
	}
	public BigDecimal getPenaltyToCustomer() {
		return penaltyToCustomer;
	}
	public void setPenaltyToCustomer(BigDecimal penaltyToCustomer) {
		this.penaltyToCustomer = penaltyToCustomer;
	}
	public String getPenaltyCurrencyCode() {
		return penaltyCurrencyCode;
	}
	public void setPenaltyCurrencyCode(String penaltyCurrencyCode) {
		this.penaltyCurrencyCode = penaltyCurrencyCode;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public boolean isHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
	public String getInvoiceMode() {
		return invoiceMode;
	}
	public void setInvoiceMode(String invoiceMode) {
		this.invoiceMode = invoiceMode;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public String getGuaranteeRule() {
		return guaranteeRule;
	}
	public void setGuaranteeRule(String guaranteeRule) {
		this.guaranteeRule = guaranteeRule;
	}
	public String getPrepayRule() {
		return prepayRule;
	}
	public void setPrepayRule(String prepayRule) {
		this.prepayRule = prepayRule;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRatePlanName() {
		return ratePlanName;
	}
	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}
	public Boolean getIsCancelable() {
		return isCancelable;
	}
	public void setIsCancelable(Boolean isCancelable) {
		this.isCancelable = isCancelable;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public BigDecimal getCoupon() {
		return coupon;
	}
	public void setCoupon(BigDecimal coupon) {
		this.coupon = coupon;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	public int getNumberOfCustomers() {
		return numberOfCustomers;
	}
	public void setNumberOfCustomers(int numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public HotelSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(HotelSupplier supplier) {
		this.supplier = supplier;
	}
	public HotelDealer getDealer() {
		return dealer;
	}
	public void setDealer(HotelDealer dealer) {
		this.dealer = dealer;
	}
	public void createOrder(JDOrderCreateCommand command,
			OrderCreateResultDTO dto) {
		setId(UUIDGenerator.getUUID());
		HotelDealer dealer = new HotelDealer();
		dealer.setId(command.getDealerProjcetId());
		setDealer(dealer);
		setDealerOrderNo(command.getDealerOrderNO());
		setOrderNo(command.getSupplierProjcetId()+command.getDealerOrderNO());
		setSupplierOrderNo(dto.getOrderId()+"");
		HotelSupplier supplier = new HotelSupplier();
		supplier.setId(command.getSupplierProjcetId());
		setSupplier(supplier);
		setStatus("0");
		setHotelName(command.getHotelName());
		setHotelNo(command.getHotelNO());
		setIsCancelable(true);
		setCancelTime(dto.getCancelTime());
		setRoomName(command.getRoomName());
		setCreationDate(new Date());
		setRatePlanName(command.getRatePlanName());
		setTotalPrice(command.getTotalPrice());
		OrderCreateCommandDTO createDTO = command.getOrderCreateDTO();
		if(createDTO != null){
			setHasInvoice(createDTO.isNeedInvoice());
			if(createDTO.isNeedInvoice()){
				setInvoiceMode(createDTO.getInvoice().getTitle());
				Invoice invoice = new Invoice();
				BeanUtils.copyProperties(createDTO.getInvoice(), invoice);
				setInvoice(invoice);
			}
			setNumberOfCustomers(createDTO.getNumberOfCustomers());
			setNumberOfRooms(createDTO.getNumberOfRooms());
			setArrivalDate(createDTO.getArrivalDate());
			setDepartureDate(createDTO.getDepartureDate());
		}
	}

}