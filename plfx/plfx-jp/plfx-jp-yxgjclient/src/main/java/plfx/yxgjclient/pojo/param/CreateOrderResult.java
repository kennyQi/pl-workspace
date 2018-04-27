package plfx.yxgjclient.pojo.param;

import java.util.List;

/**
 * 创建订单返回参数列表
 * 
 * @author guotx 2015-07-07
 * 
 */
public class CreateOrderResult {
	/**
	 * 各航段信息
	 */
	private List<SegmentInfo> segmentInfos;
	/**
	 * 各乘机人信息
	 */
	private List<PassengerInfo> passengerInfos;
	/**
	 * 价格明细
	 */
	private List<PriceDetailInfo> priceDetailInfos;
	/**
	 * 联系人信息
	 */
	private ContacterInfo contacterInfo;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 供应商营业时间 格式“HH:mm-HH:mm” ,24 小时制 如： “8： 00-24： 00”
	 */
	private String workTime;
	/**
	 * 退票时间段 格式“HH:mm-HH:mm” ,24 小时制 如： “8： 00-24： 00”
	 */
	private String refundTime;
	/**
	 * 订单预定时间 yyyy-MM-dd HH:mm:ss
	 */
	private String bookTime;
	/**
	 * 预定保留时间 yyyy-MM-dd HH:mm:ss
	 */
	private String bookRemainTime;
	/**
	 * 采购备注
	 */
	private String memo;
	/**
	 * 订单总支付价格
	 */
	private String totalPrice;
	/**
	 * 支持的支付方式 1- 汇付 2.-支付宝，多个支付方式之间用^分隔
	 */
	private String payPlatform;
	/**
	 * 订单状态
	 */
	private String ordState;

	public List<SegmentInfo> getSegmentInfos() {
		return segmentInfos;
	}

	public void setSegmentInfos(List<SegmentInfo> segmentInfos) {
		this.segmentInfos = segmentInfos;
	}

	public List<PassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}

	public void setPassengerInfos(List<PassengerInfo> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}

	public List<PriceDetailInfo> getPriceDetailInfos() {
		return priceDetailInfos;
	}

	public void setPriceDetailInfos(List<PriceDetailInfo> priceDetailInfos) {
		this.priceDetailInfos = priceDetailInfos;
	}

	public ContacterInfo getContacterInfo() {
		return contacterInfo;
	}

	public void setContacterInfo(ContacterInfo contacterInfo) {
		this.contacterInfo = contacterInfo;
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

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getBookTime() {
		return bookTime;
	}

	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	public String getBookRemainTime() {
		return bookRemainTime;
	}

	public void setBookRemainTime(String bookRemainTime) {
		this.bookRemainTime = bookRemainTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getOrdState() {
		return ordState;
	}

	public void setOrdState(String ordState) {
		this.ordState = ordState;
	}

}
