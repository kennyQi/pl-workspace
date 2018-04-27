package plfx.jd.pojo.dto.plfx.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
import plfx.jd.pojo.dto.ylclient.order.InvoiceDTO;


/**
 * 
 * @类功能说明：酒店订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午4:55:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class HotelOrderDTO extends BaseJDDTO {
	/**
	 * 供应商平台
	 */
	private SupplierDTO supplier;
	/**
	 * 经销商订单遍号
	 */
	private String dealerOrderNo;
	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 供应商平台订单号
	 */
	private String supplierOrderNo;
	
	/**
	 * 经销商
	 */
	private DealerDTO dealer;
	
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 对用户展示的订单状态
	 */
	private Long showStatus;
	/**
	 * 订单产生的罚金
	 */
	private BigDecimal penaltyToCustomer;
	/**
	 * 罚金货币类型
	 */
	private String penaltyCurrencyCode;
	/**
	 * 最晚取消时间
	 */
	private Date cancelTime;
	/**
	 * 是否要发票
	 */
	private boolean hasInvoice;
	/**
	 * 发票模型
	 */
	private String invoiceMode;
	/**
	 * 发票
	 */
	private InvoiceDTO invoice;
	/**
	 * 担保规则
	 */
	private String guaranteeRule;
	/**
	 * 预付规则
	 */
	private String prepayRule;
	/**
	 * 酒店名称
	 */
	private String hotelName;
	/**
	 * 酒店编号
	 */
	private String hotelNo;
	/**
	 * 房间名称
	 */
	private String roomName;
	/**
	 * 产品名称
	 */
	private String ratePlanName;
	/**
	 * 当前是否可以取消
	 */
	private Boolean isCancelable;
	/**
	 * 预约日期
	 */
	private Date creationDate;
	/**
	 * 优惠
	 */
	private BigDecimal coupon;
	/**
	 * 入住日期
	 */
	private Date arrivalDate;
	/**
	 * 离店日期
	 */
	private Date departureDate;
	/**
	 * 房间数量
	 */
	private int numberOfRooms;
	/**
	 * 客人数量
	 */
	private int numberOfCustomers;
	/**
	 * 住客
	 */
	private List<CustomerDTO> customers;
	
	private Double totalPrice;
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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
	
	
	public SupplierDTO getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDTO supplier) {
		this.supplier = supplier;
	}
	public DealerDTO getDealer() {
		return dealer;
	}
	public void setDealer(DealerDTO dealer) {
		this.dealer = dealer;
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
	public InvoiceDTO getInvoice() {
		return invoice;
	}
	public void setInvoice(InvoiceDTO invoice) {
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
	public String getHotelNo() {
		return hotelNo;
	}
	public void setHotelNo(String hotelNo) {
		this.hotelNo = hotelNo;
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
	public List<CustomerDTO> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}
	
	
	
}