package plfx.yeexing.pojo.command.order;

import hg.common.component.BaseCommand;



/****
 * 
 * @类功能说明：下单COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:07:20
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPRefundTicketSpiCommand extends BaseCommand {

	/**
	 * 订单号   
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个订单
	 */
	private String orderid;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 乘客姓名   
	 * 多个乘客之间用 ^ 分隔
	 */
	private String passengerName;

	/**
	 * 机票票号  
	 * 票号之间用 ^分隔，并与姓名相对应
	 */
	private String airId;
	
	/**
	 * 乘客姓名和机票信息
	 * |分割乘客和票号   
	 * ,
	 */
	private String passengerInfos;
	
	/**
	 * 申请种类
	 * 1.当日作废2.自愿退票3.非自愿退票 4.差错退款 5.其他
	 */
	private String refundType;
	
	/***
	 *申请理由
	 */
	private String refundMemo;

	/**
	 * 要退的航段  
	 * 格式：SHA-PEK(只能是第一航段已经使用，才能退第二航段，否则全退)
	 */
	private String refundSegment;

	/** 
	 * 退票通知地址     
	 * 退票处理成功后异步通知url（合作伙伴自行开发）
	 */
	private String refundNotifyUrl;
	
	/***
	 * 合作伙伴用户名
	 */
	private String userName;
	
	/***
	 * 签名  所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	/***
	 * 密钥
	 */
	private String key;
	/**
	 * 来自经销商的ID
	 * 
	 */
	private String fromDealerId;
	
	public String getFromDealerId() {
		return fromDealerId;
	}
	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getAirId() {
		return airId;
	}
	public void setAirId(String airId) {
		this.airId = airId;
	}
	public String getPassengerInfos() {
		return passengerInfos;
	}
	public void setPassengerInfos(String passengerInfos) {
		this.passengerInfos = passengerInfos;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getRefundMemo() {
		return refundMemo;
	}
	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
	public String getRefundSegment() {
		return refundSegment;
	}
	public void setRefundSegment(String refundSegment) {
		this.refundSegment = refundSegment;
	}
	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}
	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	
	
}
