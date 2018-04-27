package hg.dzpw.dealer.client.common.publish;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @类功能说明：经销商接口发布消息请求
 * @类修改者：
 * @修改日期：2015-4-28上午10:02:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-28上午10:02:46
 */
public class PublishEventRequest {

	/** 景区启用[消息为ID] */
	public final static String EVENT_SCENIC_SPOT_ISSUE = "SCENIC_SPOT_ISSUE";
	/** 景区禁用[消息为ID] */
	public final static String EVENT_SCENIC_SPOT_FINISH = "SCENIC_SPOT_FINISH";
	/** 门票政策上架[消息为门票政策ID] */
	public final static String EVENT_TICKET_POLICY_ISSUE = "TICKET_POLICY_ISSUE";
	/** 门票政策下架[消息为门票政策ID] */
	public final static String EVENT_TICKET_POLICY_FINISH = "TICKET_POLICY_FINISH";
	/** 订单被关闭[消息为订单ID] */
	public final static String EVENT_TICKET_ORDER_CLOSE = "TICKET_ORDER_CLOSE";
	/** 订单状态已更新[消息为订单ID] */
	public final static String EVENT_TICKET_ORDER_STATUS_CHANGE = "TICKET_ORDER_STATUS_CHANGE";
	/** 门票退款成功[消息为门票票号] */
	public final static String EVENT_TICKET_REFUND_SUCCESS = "TICKET_REFUND_SUCCESS";
	/** 门票可退款[消息为门票票号] */
	public final static String EVENT_TICKET_CAN_APPLY_REFUND = "EVENT_TICKET_CAN_APPLY_REFUND";
	
	public static String ENCODING = "UTF-8";

	/**
	 * 事件
	 */
	private String event;

	/**
	 * 消息
	 */
	private String message;
	
	/**
	 * 经销商代码
	 */
	private String dealerKey;
	
	/**
	 * @方法功能说明：请求参数字符串
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-28上午10:23:14
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String paramsString() {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("event=").append(URLEncoder.encode(event, ENCODING))
					.append("&message=")
					.append(URLEncoder.encode(message, ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public PublishEventRequest(String event, String message) {
		this.event = event;
		this.message = message;
	}
	
	/**
	 * @类名：PublishEventRequest.java
	 * @描述：推送给指定经销商
	 * @@param event
	 * @@param message
	 * @@param dealerKey
	 */
	public PublishEventRequest(String event, String message, String dealerKey){
		this.event = event;
		this.message = message;
		this.dealerKey = dealerKey;
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

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

}
