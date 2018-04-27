package plfx.mp.tcclient.tc.domain.order;

public class Order {
	/**
	 * \订单流水号
	 */
	private String serialId;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单状态
	 */
	private String createDate;
	/**
	 * 旅游时间
	 */
	private String travelDate;
	/**
	 * 景点ID
	 */
	private Integer sceneryId;
	/**
	 * 景点名称
	 */
	private String sceneryName;
	/**
	 * 预订人
	 */
	private String bookingMan;
	/**
	 * 预订人电话
	 */
	private String bookingMobile;
	/**
	 * 游玩人
	 */
	private String guestName;
	/**
	 * 游玩人电话
	 */
	private String guestMobile;
	/**
	 * 票品名称
	 */
	private String ticketName;
	/**
	 * 票品ID
	 */
	private Integer ticketTypeId;
	/**
	 * 票数
	 */
	private Integer ticketQuantity;
	/**
	 * 单张票价
	 */
	private Double ticketPrice;
	/**
	 * 总票价
	 */
	private Double ticketAmount;
	/**
	 * 返现金额
	 */
	private Double prizeAmount;
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 是否可取消
	 */
	private Integer enableCancel;
	/**
	 * 支付状态
	 */
	private Integer currentPayStatus;
	private String refundTime;
	public static final Integer PAY_STATUS_NO=0;//无需支付
	public static final Integer PAY_STATUS_READY=1;//待支付
	public static final Integer PAY_STATUS_OVER=2;//已支付
	public static final Integer PAY_STATUS_PART_OVER=3;//部分支付
	public static final Integer PAY_STATUS_CANCEL=4;//已退款
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public Integer getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(Integer sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public String getBookingMan() {
		return bookingMan;
	}
	public void setBookingMan(String bookingMan) {
		this.bookingMan = bookingMan;
	}
	public String getBookingMobile() {
		return bookingMobile;
	}
	public void setBookingMobile(String bookingMobile) {
		this.bookingMobile = bookingMobile;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getGuestMobile() {
		return guestMobile;
	}
	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public Integer getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(Integer ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public Integer getTicketQuantity() {
		return ticketQuantity;
	}
	public void setTicketQuantity(Integer ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	public Double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public Double getTicketAmount() {
		return ticketAmount;
	}
	public void setTicketAmount(Double ticketAmount) {
		this.ticketAmount = ticketAmount;
	}
	public Double getPrizeAmount() {
		return prizeAmount;
	}
	public void setPrizeAmount(Double prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getEnableCancel() {
		return enableCancel;
	}
	public void setEnableCancel(Integer enableCancel) {
		this.enableCancel = enableCancel;
	}
	public Integer getCurrentPayStatus() {
		return currentPayStatus;
	}
	public void setCurrentPayStatus(Integer currentPayStatus) {
		this.currentPayStatus = currentPayStatus;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}
	
}
