package plfx.yxgjclient.pojo.param;

import java.util.List;

/**
 * 申请退废票接口返回参数列表
 * @author guotx
 * 2015-07-06
 */
public class ApplyRefundResult {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 单张操作信息
	 */
	private List<PerOperationIsSuc> perPassengerIsSucs;
	
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

	public List<PerOperationIsSuc> getPerPassengerIsSucs() {
		return perPassengerIsSucs;
	}

	public void setPerPassengerIsSucs(List<PerOperationIsSuc> perOperationIsSucs) {
		this.perPassengerIsSucs = perOperationIsSucs;
	}
}
