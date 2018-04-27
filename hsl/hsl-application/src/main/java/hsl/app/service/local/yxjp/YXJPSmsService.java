package hsl.app.service.local.yxjp;

import hg.common.util.DateUtil;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hsl.app.component.config.SysProperties;
import hsl.domain.model.yxjp.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 易行机票短信服务
 * session未关闭才可调用
 * </pre>
 *
 * @author zhurz
 * @since 1.7
 */
@Service
public class YXJPSmsService {

	@Autowired
	private SMSUtils smsUtils;

	/**
	 * 短信发送线程池
	 */
	private ExecutorService smsExec = Executors.newFixedThreadPool(10);

	// ----------- 短信模版 -----------

	/**
	 * 支付成功且向分销下单成功（参数：签名，订单号，网址，客服电话）
	 */
	public static final String sms1 = "【%s】您的订单%s，已经购买成功，出票完成后将短信通知，请您放心安排行程。查询订单请点击%s，客服电话%s。";
	/**
	 * <pre>
	 * 出票成功（参数：签名，订单号，机票名称，循环乘客与票号，网址，客服电话）
	 * 机票名称=2014-08-11，出发地机场航站楼到目的地机场航站楼的＋航班号＋航班（出发时间/到达时间）
	 * 循环乘客与票号=<乘客姓名>票号：<xxx－xxxxxxxxx>（顿号、分隔）
	 * </pre>
	 */
	public static final String sms2 = "【%s】您的订单%s，%s已出票。%s。请提前2个小时到机场办理乘机手续。详情请点击%s，客服电话%s。";
	/**
	 * 出票失败（参数：签名，订单号，网址，客服电话）
	 */
	public static final String sms3 = "【%s】对不起，您的订单%s出票失败，已申请退款至银行卡，具体到账时间请咨询相应银行，详情请点击%s，客服电话%s。";
	/**
	 * <pre>
	 * 申请退废票（参数：签名，订单号，循环乘客与票号，网址，客服电话）
	 * 循环乘客与票号=<乘客姓名>票号：<xxx－xxxxxxxxx>（顿号、分隔）
	 * </pre>
	 */
	public static final String sms4 = "【%s】您的订单%s，%s，申请退废票，详情请点击%s，客服电话%s。";
	/**
	 * 退废票成功（参数：签名，订单号，循环乘客与票号，网址，客服电话）
	 */
	public static final String sms5 = "【%s】您的订单%s，%s，退票成功，票款会在7-15个工作日内返回到您的银行账户，详情请点击%s，客服电话%s。";
	/**
	 * 退废票失败（参数：签名，订单号，循环乘客与票号，网址，客服电话）
	 */
	public static final String sms6 = "【%s】对不起，您的订单%s，%s，退票失败，详情请点击%s，客服电话%s。";

	/**
	 * 向分销下单成功发短信
	 *
	 * @param order 易行机票订单
	 */
	public void sendSmsOnOutOrderSuccess(YXJPOrder order) {
		if (order == null) return;

		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，网址，客服电话
				String.format(sms1,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						getWebSiteUrl(),
						getServicePhone()
				));

	}

	/**
	 * 出票成功发短信
	 *
	 * @param order 易行机票订单
	 */
	public void sendSmsOnOutTicketSuccess(YXJPOrder order) {
		if (order == null) return;

		// 航班信息
		YXJPOrderFlightBaseInfo flight = order.getFlight().getBaseInfo();

		// 机票名称
		String ticketName = String.format("[%s%s(%s)到%s%s(%s)][%s]机票",
				flight.getDepartAirport(), flight.getDepartTerm(), DateUtil.formatDateTime(flight.getStartTime(), "yyyy-MM-dd HH:mm"),
				flight.getArrivalAirport(), flight.getArrivalTerm(), DateUtil.formatDateTime(flight.getEndTime(), "yyyy-MM-dd HH:mm"),
				flight.getFlightNo()
		);

		// 循环乘客与票号
		String passengerTicketNo = buildPassengerNameAndTicketNo(order.getPassengers());

		// 没有乘客出票成功
		if (passengerTicketNo.length() == 0)
			return;

		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，机票名称，循环乘客与票号，网址，客服电话
				String.format(sms2,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						ticketName,
						passengerTicketNo,
						getWebSiteUrl(),
						getServicePhone()
				));
	}

	/**
	 * 出票失败发短信
	 *
	 * @param order 易行机票订单
	 */
	public void sendSmsOnOutTicketFailure(YXJPOrder order) {

		if (order == null) return;

		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，网址，客服电话
				String.format(sms3,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						getWebSiteUrl(),
						getServicePhone()
				));
	}

	/**
	 * 退票票申请成功发短信
	 *
	 * @param apply 退废票申请
	 */
	public void sendSmsOnRefundApply(YXJPOrderRefundApply apply) {

		if (apply == null || !apply.getApplySuccess()) return;
		YXJPOrder order = apply.getFromOrder();
		if (order == null) return;

		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，循环乘客与票号，网址，客服电话
				String.format(sms4,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						buildPassengerNameAndTicketNo(apply.getPassengers()),
						getWebSiteUrl(),
						getServicePhone()
				));
	}

	/**
	 * 退废票成功发短信
	 *
	 * @param refundResult 退废票申请结果
	 */
	public void sendSmsOnRefundSuccess(YXJPOrderRefundResult refundResult) {

		if (refundResult == null) return;
		// 乘客的状态如果全部是退废成功已退款，则不会发送短信
		if (refundResult.manager().isAllRebackSuccess()) return;
		YXJPOrder order = refundResult.getFromOrder();
		if (order == null) return;


		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，循环乘客与票号，网址，客服电话
				String.format(sms5,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						buildPassengerNameAndTicketNo(refundResult.getPassengers()),
						getWebSiteUrl(),
						getServicePhone()
				));
	}

	/**
	 * 退废票失败发短信
	 *
	 * @param refundResult 退废票申请结果
	 */
	public void sendSmsOnRefundFailure(YXJPOrderRefundResult refundResult) {

		if (refundResult == null) return;
		YXJPOrder order = refundResult.getFromOrder();
		if (order == null) return;

		// 异步发送短信
		asyncSendSms(order.getLinkman().getLinkMobile(),
				// 参数：签名，订单号，循环乘客与票号，网址，客服电话
				String.format(sms6,
						getSmsSign(),
						order.getBaseInfo().getOrderNo(),
						buildPassengerNameAndTicketNo(refundResult.getPassengers()),
						getWebSiteUrl(),
						getServicePhone()
				));
	}

	/**
	 * 异步发送短信
	 *
	 * @param toMobile 目标手机号
	 * @param msg      短信
	 */
	private void asyncSendSms(final String toMobile, final String msg) {
		smsExec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					HgLogger.getInstance().info("zhurz", String.format("易行机票事件，发送短信->%s->%s", toMobile, msg));
					smsUtils.sendSms(toMobile, msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 循环输出乘客姓名与票号，顿号间隔。
	 *
	 * @param passengers 乘客
	 * @return
	 */
	private String buildPassengerNameAndTicketNo(List<YXJPOrderPassenger> passengers) {
		// 检查乘客
		if (passengers == null || passengers.size() == 0) return "";
		// 循环乘客与票号
		StringBuilder passengerTicketNo = new StringBuilder();
		for (YXJPOrderPassenger passenger : passengers) {
			if (StringUtils.isBlank(passenger.getTicket().getTicketNo()))
				continue;
			if (passengerTicketNo.length() > 0)
				passengerTicketNo.append("、");
			passengerTicketNo.append(passenger.getBaseInfo().getName())
					.append("票号：").append(passenger.getTicket().getTicketNo());
		}
		return passengerTicketNo.toString();
	}

	/**
	 * 短信签名
	 *
	 * @return
	 */
	private String getSmsSign() {
		return SysProperties.getInstance().get("sms_sign", "票量旅游");
	}

	/**
	 * 客服电话
	 *
	 * @return
	 */
	private String getServicePhone() {
		return SysProperties.getInstance().get("service_phone", "0571-28280813");
	}

	/**
	 * 网址
	 *
	 * @return
	 */
	private String getWebSiteUrl() {
		return "http://www.ply365.com";
	}
}
