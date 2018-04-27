package plfx.yxgjclient.pojo.param;
/**
 * 查询退废票信息结果参数列表
 * @author guotx
 * 2015-07-10
 */
public class QueryRefundStatesResult {
	/**
	 * 退票类型
	 * 0废票 1退票
	 */
	private String ordDetRtnType;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 票号
	 */
	private String ordDetEticketNo;
	/**
	 * 机票状态
	 * 3.申请退票 4.退票完成 7.申请废票 9.退废票被拒绝
	 * 10.退废票被锁定 11.再次申请退废票 12.申请退废票解锁 13.供应商拒绝退废票
	 * 14.必须退废票 15.退票时供应商退分润成
	 */
	private String ticketType;
	/**
	 * 乘客姓名
	 */
	private String passengerName;
	/**
	 * 申请退票时间
	 * yyyy-MM-dd
	 */
	private String ordDetReqRtnTime;
	/**
	 * 退票完成时间
	 * yyyy-MM-dd HH:mm:ss，若退票没有完成，则为空
	 */
	private String rtnModiTime;
	/**
	 * 实付金额
	 */
	private String refundPrice;
	/**
	 * 退款手续费
	 */
	private String refundFee;
	public String getOrdDetRtnType() {
		return ordDetRtnType;
	}
	public void setOrdDetRtnType(String ordDetRtnType) {
		this.ordDetRtnType = ordDetRtnType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExtOrderId() {
		return extOrderId;
	}
	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}
	public String getOrdDetEticketNo() {
		return ordDetEticketNo;
	}
	public void setOrdDetEticketNo(String ordDetEticketNo) {
		this.ordDetEticketNo = ordDetEticketNo;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getOrdDetReqRtnTime() {
		return ordDetReqRtnTime;
	}
	public void setOrdDetReqRtnTime(String ordDetReqRtnTime) {
		this.ordDetReqRtnTime = ordDetReqRtnTime;
	}
	public String getRtnModiTime() {
		return rtnModiTime;
	}
	public void setRtnModiTime(String rtnModiTime) {
		this.rtnModiTime = rtnModiTime;
	}
	public String getRefundPrice() {
		return refundPrice;
	}
	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
}
