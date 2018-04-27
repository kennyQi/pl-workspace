package hg.payment.domain.model.refund;

import java.util.Date;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.payment.domain.model.event.ReceivePayRequestEvent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * 
 *@类功能说明：退款记录状态
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月28日上午10:35:45
 *
 */
@Embeddable
public class RefundProcessor {

	/**
	 * 退款状态
	 */
	@Column(name="REFUND_STATUS")
	private Integer refundStatus = REFUND_CREATED;

	/**
	 * 通知业务系统退款状态
	 */
	@Column(name="NOTIFY_STATUS")
	private Integer notifyStatus = NOTIFY_CLIENT_REFUND_UNDO;
	
	/****************************************************/
	
	/*** 已创建退款记录，未推送给第三方退款 ***/
	public static Integer REFUND_CREATED = 1;
	
	/*** 推送给第三方退款成功  ***/
	public static Integer SUBMIT_TO_REFUND_SUCCESS = 2;
	/*** 推送给第三方退款失败  ***/
	public static Integer SUBMIT_TO_REFUND_FAIL = 3;
	
	/*** 第三方反馈退款成功  ***/
	public static Integer REFUND_SUCCESS = 4;
	/*** 第三方反馈退款失败  ***/
	public static Integer REFUND_FAIL = 5;
	
	/****************************************************/
	
	/*** 未通知业务系统 ***/
	public static Integer NOTIFY_CLIENT_REFUND_UNDO = 1;
	
	/*** 通知业务系统退款成功 ***/
	public static Integer NOTIFY_CLIENT_REFUND_SUCCESS = 2;
	/*** 通知业务系统退款失败 ***/
	public static Integer NOTIFY_CLIENT_REFUND_FAIL = 3;
	
	
	/**
	 * 已推送给第三方
	 */
	public Boolean isSubmitted() {
		switch (refundStatus) {
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
	 * 已确认退款失败，包括第三方告知及未提交给第三方但本地失败
	 */
	public Boolean isFailed() {
		switch (refundStatus) {
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
	 * 已得到第三方最终反馈退款成功
	 */
	public Boolean isRefundSuccess() {
		switch (refundStatus) {
		case 4:
			return true;
		default:
			return false;
		}
	}

	/****************************************************/
	
	/**
	 * 是否已反馈业务系统退款结果
	 */
	public Boolean isNotifyRefund() {
		switch (notifyStatus) {
		case 2:
			return true;
		case 3:
			return true;
		default:
			return false;
		}
	}
	
	
	public void receiveRefundRequest(Refund refund){
		refundStatus = REFUND_CREATED;
		notifyStatus = NOTIFY_CLIENT_REFUND_UNDO;
		ReceivePayRequestEvent event = new ReceivePayRequestEvent();
		event.setId(UUIDGenerator.getUUID());
		event.setHappenedDate(new Date());
		String eventLog = "已收到来自:" + refund.getPayOrder().getPaymentClient().getName()
				+ "的退款请求，生成退款记录" + refund.getId();
		HgLogger.getInstance().info("luoyun", eventLog);
	}
	
	public void submitRefundRequestSuccess(Refund refund){
		refundStatus = SUBMIT_TO_REFUND_SUCCESS;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "已成功向"
				+ refund.getPayOrder().getPayChannel().getName() + "发送退款请求";
		HgLogger.getInstance().info("luoyun", eventLog);
	}
	
	public void submitRefundRequestFail(Refund refund){
		refundStatus = SUBMIT_TO_REFUND_FAIL;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "向"
				+ refund.getPayOrder().getPayChannel().getName() + "发送退款请求失败";
		HgLogger.getInstance().info("luoyun", eventLog);
	}
	
	public void receiveRefundSuccessNotice(Refund refund) {
		refundStatus = REFUND_SUCCESS;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "已接收到退款成功通知";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void receiveRefundFailNotice(Refund refund) {
		refundStatus = REFUND_FAIL;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "已接收到退款失败通知，原因:"
				+ refund.getRefundRemark();
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void noticeRefundResultSuccess(Refund refund) {
		notifyStatus = NOTIFY_CLIENT_REFUND_SUCCESS;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "发送退款结果通知到"
				+ refund.getPayOrder().getPaymentClient().getName() + "成功";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public void noticeRefundResultFail(Refund refund) {
		notifyStatus = NOTIFY_CLIENT_REFUND_FAIL;
		String eventLog = "支付单" + refund.getPayOrder().getId() + "发送退款结果通知到"
				+ refund.getPayOrder().getPaymentClient().getName() + "失败";
		HgLogger.getInstance().info("luoyun", eventLog);
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}
	
	
	
	
}
