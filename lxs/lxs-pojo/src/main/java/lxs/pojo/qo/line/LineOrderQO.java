package lxs.pojo.qo.line;
import hg.common.component.BaseQo;

import java.util.List;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年2月3日上午10:46:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年2月3日上午10:46:13
 *
 */
@SuppressWarnings("serial")
public class LineOrderQO extends BaseQo{
	
	/**
	 * 是否催促过付款
	 */
	private String haveSendedSMS;
	
	/**
	 * 是否催促过付款标志
	 */
	private String haveSendedSMSFlag;
	
	/**
	 * 订单号
	 */
	 
	private String orderId;
	/**
	 * 线路编号
	 */
	private String lineNumber;
	/**
	 * 线路ID
	 */
	private String lineId;
	/**
	 * 线路名称
	 */
	private String lineName;
	/**
	 * 预订人
	 */
	private String booker;
	/**
	 * 下单人ID
	 */
	private String userId;
	/**
	 * 下单开始时间
	*/
	private String startTime;
	/**
	 * 下单结束时间
	*/
	private String endTime;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 支付状态
	*/
	private String payStatus;
	
	/**
	 * 经销商订单号
	*/
	private String dealerOrderNo;
	/**
	 *支付状态列表 
	 */
	private List<Integer> payStatusList;
	
	
	public List<Integer> getPayStatusList() {
		return payStatusList;
	}
	public void setPayStatusList(List<Integer> payStatusList) {
		this.payStatusList = payStatusList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getBooker() {
		return booker;
	}
	public void setBooker(String booker) {
		this.booker = booker;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getDealerOrderNo() {
		return dealerOrderNo;
	}
	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getHaveSendedSMS() {
		return haveSendedSMS;
	}
	public void setHaveSendedSMS(String haveSendedSMS) {
		this.haveSendedSMS = haveSendedSMS;
	}
	public String getHaveSendedSMSFlag() {
		return haveSendedSMSFlag;
	}
	public void setHaveSendedSMSFlag(String haveSendedSMSFlag) {
		this.haveSendedSMSFlag = haveSendedSMSFlag;
	}
	
}