package plfx.yxgjclient.pojo.param;
/**
 * 订单信息
 * @author guotx
 * 2015-07-14
 */
public class OrderInfo {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * Pnr,如没有，则为空
	 */
	private String ordPnr;
	/**
	 * 换编码出票后的 pnr，如没有，则为空
	 */
	private String ordNewPnr;
	/**
	 * 大 PNR，如没有，则为空
	 */
	private String ordBPnr;
	/**
	 * 订单状态
	 * 1.等待支付 2.支付成功 3.处理完成 4.客户删除
	 * 5.退票完成 6.自动出票失败 7.申请取消 8.供应商拒绝出票
	 * 9.拒绝出票 10.废票完成 11.申请撤单 12.平台审核
	 * 13.平台审核退回 14.供应审核 15.供应审核退回
	 */
	private String ordState;
	/**
	 * 订单类型
	 */
	private String ordType;
	/**
	 * 订单预定时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String ordBookTime;
	/**
	 * 预定保留时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String ordHoldingTime;
	/**
	 * 航程信息
	 * 1.单程 2.来回程 3.缺口程 4.缺口来回程 5.散拼团行程
	 */
	private String ordLineType;
	/**
	 * 订单总支付价格（客户）
	 */
	private String totalPrice;
	/**
	 * 支付公司
	 * 1.汇付 2.支付宝
	 */
	private String payplatform;
	/**
	 * 支付公司流水号
	 */
	private String tradeNo;
	/**
	 * 支付时间
	 */
	private String ordPayTime;
	/**
	 * 出票时间
	 */
	private String ordGetTime;
	/**
	 * 联系人姓名
	 */
	private String contactName;
	/**
	 * 联系人电话
	 */
	private String contactPhone;
	/**
	 * 联系人邮箱
	 */
	private String contactMailAdd; 
	/**
	 * 工作时间
	 * 格式“HH:mm-HH:mm” ,24 小时制
	 * 如： “8： 00-24： 00“
	 */
	private String workTime;
	/**
	 * 退票时间段
	 * 格式“HH:mm-HH:mm” ,24 小时制
	 * 如： “8： 00-24： 00
	 */
	private String refundTime;
	/**
	 * 订单票状态
	 * 2015-07-24 16:35:52添加
	 * 文档中没有，但是接口返回报文中有
	 */
	private String ordTicketType;
	/**
	 * 支付平台类型
	 * 2015-07-24 16:40:28添加
	 * 文档中没有，但是返回接口报文中哟
	 */
	private String ordPayPlatform;
	
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
	public String getOrdPnr() {
		return ordPnr;
	}
	public void setOrdPnr(String ordPnr) {
		this.ordPnr = ordPnr;
	}
	public String getOrdNewPnr() {
		return ordNewPnr;
	}
	public void setOrdNewPnr(String ordNewPnr) {
		this.ordNewPnr = ordNewPnr;
	}
	public String getOrdBPnr() {
		return ordBPnr;
	}
	public void setOrdBPnr(String ordBPnr) {
		this.ordBPnr = ordBPnr;
	}
	public String getOrdState() {
		return ordState;
	}
	public void setOrdState(String ordState) {
		this.ordState = ordState;
	}
	public String getOrdType() {
		return ordType;
	}
	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}
	public String getOrdBookTime() {
		return ordBookTime;
	}
	public void setOrdBookTime(String ordBookTime) {
		this.ordBookTime = ordBookTime;
	}
	public String getOrdHoldingTime() {
		return ordHoldingTime;
	}
	public void setOrdHoldingTime(String ordHoldingTime) {
		this.ordHoldingTime = ordHoldingTime;
	}
	public String getOrdLineType() {
		return ordLineType;
	}
	public void setOrdLineType(String ordLineType) {
		this.ordLineType = ordLineType;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPayplatform() {
		return payplatform;
	}
	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrdPayTime() {
		return ordPayTime;
	}
	public void setOrdPayTime(String ordPayTime) {
		this.ordPayTime = ordPayTime;
	}
	public String getOrdGetTime() {
		return ordGetTime;
	}
	public void setOrdGetTime(String ordGetTime) {
		this.ordGetTime = ordGetTime;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactMailAdd() {
		return contactMailAdd;
	}
	public void setContactMailAdd(String contactMailAdd) {
		this.contactMailAdd = contactMailAdd;
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
	public String getOrdTicketType() {
		return ordTicketType;
	}
	public void setOrdTicketType(String ordTicketType) {
		this.ordTicketType = ordTicketType;
	}
	public String getOrdPayPlatform() {
		return ordPayPlatform;
	}
	public void setOrdPayPlatform(String ordPayPlatform) {
		this.ordPayPlatform = ordPayPlatform;
	}
}
