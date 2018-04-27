package plfx.yxgjclient.pojo.param;

import java.util.List;

/**
 * 申请退费票请求参数列表
 * @author guotx
 * 2015-07-06
 */
public class ApplyRefundParams extends BaseParam{
	/**
	 * 易行订单号，必填
	 */
	private String orderId;
	/**
	 * 外部订单号，非必填
	 */
	private String extOrderId;
	/**
	 * 机票信息，必填
	 */
	private List<PassengerInfo> passengerInfos;
	/**
	 * 申请种类，必填
	 * 1. 当日作废
	 * 2. 自愿退票
	 * 3. 非自愿退票
	 * 4. 其他
	 * 备注：一次申请只能选择一种申请理
	 * 由，多个乘客可以使用同一种种类
	 */
	private String refundType;
	/**
	 * 申请理由，必填
	 */
	private String refundMemo;
	/**
	 * 退票通知地址，必填
	 */
	private String refundNotifyUrl;

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

	public List<PassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}

	public void setPassengerInfos(List<PassengerInfo> passengerInfos) {
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

	public String getRefundNotifyUrl() {
		return refundNotifyUrl;
	}

	public void setRefundNotifyUrl(String refundNotifyUrl) {
		this.refundNotifyUrl = refundNotifyUrl;
	}
}
