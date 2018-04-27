package plfx.yeexing.pojo.command.order;

import hg.common.component.BaseCommand;

import java.util.List;

import plfx.yeexing.pojo.dto.order.JPPassengerDTO;



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
public class JPCancelTicketSpiCommand extends BaseCommand {

	/***
	 * 合作伙伴用户名
	 * 合作伙伴在易行天下的用户名
	 */
	private String userName;
	
	/**
	 * 订单号
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个易行订单
	 */
	private String orderid;

	/**
	 * 乘客姓名 
	 * 多个乘客之间用 ^ 分隔
	 */
	private String passengerName;

	/**
	 * 取消通知地址
	 * 取消处理成功后异步通知url（合作伙伴自行开发）
	 */
	private String cancel_notify_url;
	
	/***
	 * 签名  
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	/***
	 * 密钥
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String key;
	
	/**
	 * 来自经销商的ID
	 * 
	 */
	private String fromDealerId;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	public String getDealerOrderId() {
		return dealerOrderId;
	}


	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public String getCancel_notify_url() {
		return cancel_notify_url;
	}


	public void setCancel_notify_url(String cancel_notify_url) {
		this.cancel_notify_url = cancel_notify_url;
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


	public String getFromDealerId() {
		return fromDealerId;
	}


	public void setFromDealerId(String fromDealerId) {
		this.fromDealerId = fromDealerId;
	}
		
}
