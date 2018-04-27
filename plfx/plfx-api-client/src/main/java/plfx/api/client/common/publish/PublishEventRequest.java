package plfx.api.client.common.publish;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @类功能说明：事件消息通知请求
 * @类修改者：
 * @修改日期：2015-7-2下午3:44:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:44:11
 */
public class PublishEventRequest {

	public final static String ENCODING = "UTF-8";
	
	// ------------------- 事件 -------------------
	/**
	 * 事件：国际机票订单取消
	 */
	public final static String EVENT_GJCancelOrder = "GJCancelOrder";
	/**
	 * 事件：国际机票订单审核完成
	 */
	public final static String EVENT_GJOrderAuditEnd = "GJOrderAuditEnd";
	/**
	 * 事件：国际机票订单支付成功
	 */
	public final static String EVENT_GJPaySuccess = "GJPaySuccess";
	/**
	 * 事件：国际机票退废票申请完成
	 */
	public final static String EVENT_GJRefundTicket = "GJRefundTicket";
	/**
	 * 事件：国际机票拒绝出票
	 */
	public final static String EVENT_GJOutTicketFailure = "GJOutTicketFailure";
	/**
	 * 事件：国际机票出票成功
	 */
	public final static String EVENT_GJOutTicketSuccess = "GJOutTicketSuccess";
	
	/**
	 * 事件
	 */
	private String event;

	/**
	 * 消息
	 */
	private String message;

	/**
	 * 签名=md5(event+message+经销商密钥)
	 */
	private String sign;
	
	/**
	 * @方法功能说明：请求参数字符串
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-28上午10:23:14
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String paramsString(String secretKey) {

		if (secretKey == null)
			throw new RuntimeException("缺少经销商密钥");
		if (event == null)
			throw new RuntimeException("缺少经事件");
		if (message == null)
			throw new RuntimeException("缺少经事件消息");

		StringBuilder sb = new StringBuilder();
		try {
			sb.append("event=")
					.append(URLEncoder.encode(event, ENCODING))
					.append("&message=")
					.append(URLEncoder.encode(message, ENCODING))
					.append("&sign=")
					.append(URLEncoder.encode(
							DigestUtils.md5Hex(event + message + secretKey),
							ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public PublishEventRequest() {
	}

	public PublishEventRequest(String event, String message) {
		this.event = event;
		this.message = message;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public static void main(String[] args) {
		PublishEventRequest request = new PublishEventRequest("hello",
				"法撒旦法法撒旦法ds的萨芬");
		//System.out.println(request.paramsString("123"));
		//System.out.println(request.paramsString("456"));
	}

}
