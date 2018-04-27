package hg.payment.domain.model.payorder;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.payment.domain.model.event.ReceivePayRequestEvent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @类功能说明：支付单状态
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月26日下午7:33:00
 * 
 */
@Embeddable
public class PayOrderProcessor {

	/**
	 * 订单支付状态
	 */
	@Column(name="PAY_STATUS")
	private Integer payStatus = ORDER_CREATED;

	/**
	 * 订单通知业务系统状态
	 */
	@Column(name="NOTIFY_STATUS")
	private Integer notifyStatus = NOTIFY_CLIENT_UNDO;

	/****************************************************/
	/*** 已创建支付单，未推送给第三方支付 ***/
	public static Integer ORDER_CREATED = 1;

	/*** 推送给第三方支付成功 ***/
	public static Integer SUBMIT_TO_PAY_SUCCESS = 2;
	/*** 推送给第三方支付失败 ***/
	public static Integer SUBMIT_TO_PAY_FAIL = 3;

	/*** 第三方反馈支付成功 ***/
	public static Integer PAY_SUCCESS = 4;
	/*** 第三方反馈支付失败 ***/
	public static Integer PAY_FAIL = 5;
	
	/*** 有退款记录 ***/
	public static Integer HAVE_REFUND = 6;
	
	/*** 交易关闭***/
//	public static Integer CLOSE = 7;

	/****************************************************/
	/*** 未通知业务系统 ***/
	public static Integer NOTIFY_CLIENT_UNDO = 1;
	/*** 通知业务系统成功 ***/
	public static Integer NOTIFY_CLIENT_SUCCESS = 2;
	/*** 通知业务系统失败 ***/ 
	public static Integer NOTIFY_CLIENT_FAIL = 3;

	/**
	 * 已推送给第三方
	 */
	public Boolean isSubmitted() {
		switch (payStatus) {
		case 2:
			return true;
		case 4:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 已确认支付失败，包括第三方告知及未提交给第三方但本地失败
	 */
	public Boolean isFailed() {
		switch (payStatus) {
		case 3:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}

	/****************************************************/
	
	
	/**
	 * 已得到第三方最终反馈支付成功
	 */
	public Boolean isPaySuccess() {
		switch (payStatus) {
		case 4:
			return true;
		case 6:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * 订单有退款记录
	 */
	public Boolean haveRefund(){
		switch (payStatus) {
		case 6:
			return true;
		default:
			return false;
		}
	}

	/****************************************************/
	/**
	 * 是否已反馈业务系统结果
	 */
	public Boolean isNotify() {
		switch (notifyStatus) {
		case 2:
			return true;
		case 3:
			return true;
		default:
			return false;
		}
	}
	

	public void receivePayRequest(PayOrder payOrder) {
		payStatus = ORDER_CREATED;
		notifyStatus = NOTIFY_CLIENT_UNDO;
		ReceivePayRequestEvent event = new ReceivePayRequestEvent();
		event.setId(UUIDGenerator.getUUID());
		event.setHappenedDate(new Date());
		String eventLog = "已收到来自:" + payOrder.getPaymentClient().getName()
				+ "的支付请求，生成支付单号" + payOrder.getId();
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void submitPayRequestSuccess(PayOrder payOrder) {
		payStatus = SUBMIT_TO_PAY_SUCCESS;
		String eventLog = "支付单" + payOrder.getId() + "已成功向"
				+ payOrder.getPayChannel().getName() + "发送支付请求";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void submitPayRequestFail(PayOrder payOrder) {
		payStatus = SUBMIT_TO_PAY_FAIL;
		String eventLog = "支付单" + payOrder.getId() + "向"
				+ payOrder.getPayChannel().getName() + "发送支付请求失败";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void receivePaySuccessNotice(PayOrder payOrder) {
		payStatus = PAY_SUCCESS;
		String eventLog = "支付单" + payOrder.getId() + "已接收到支付成功通知";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void receivePayFailNotice(PayOrder payOrder) {
		payStatus = PAY_FAIL;
		String eventLog = "支付单" + payOrder.getId() + "已接收到支付失败通知，原因:"
				+ payOrder.getOrderRemark();
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void noticePayResultSuccess(PayOrder payOrder) {
		notifyStatus = NOTIFY_CLIENT_SUCCESS;
		String eventLog = "支付单" + payOrder.getId() + "发送支付结果通知到"
				+ payOrder.getPaymentClient().getName() + "成功";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void noticePayResultFail(PayOrder payOrder) {
		notifyStatus = NOTIFY_CLIENT_FAIL;
		String eventLog = "支付单" + payOrder.getId() + "发送支付结果通知到"
				+ payOrder.getPaymentClient().getName() + "失败";
		HgLogger.getInstance().info("luoyun", eventLog);
	}
	
	public void haveRefund(PayOrder payOrder){
		payStatus = HAVE_REFUND;
		String eventLog = "已完成" + payOrder.getPaymentClient().getName()  + "的支付单" + payOrder.getId() + 
				"的退款请求";
		HgLogger.getInstance().info("luoyun", eventLog);
	}
	
//	public void closePayOrder(PayOrder payOrder){
//		payStatus = CLOSE;
//		String eventLog = "支付单" + payOrder.getId() + "已全额退款，交易关闭";
//		HgLogger.getInstance().info("luoyun", eventLog);
//	}
//	
	

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

}
