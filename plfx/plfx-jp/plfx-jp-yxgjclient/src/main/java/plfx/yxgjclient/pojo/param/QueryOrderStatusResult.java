package plfx.yxgjclient.pojo.param;
/**
 * 查询订单状态返回参数
 * @author guotx
 * 2015-07-07
 */
public class QueryOrderStatusResult {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 订单状态
	 * 1.等待支付 2.支付成功 3.处理完成 4.客户删除
	 * 5.退票完成 6.自动出票失败 7.申请取消 8.供应商拒绝出票
	 * 9.拒绝出票 10.废票完成 11.申请撤单 12.平台审核
	 * 13.平台审核退回 14.供应审核 15.供应审核退回
	 */
	private String ordState;
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
	public String getOrdState() {
		return ordState;
	}
	public void setOrdState(String ordState) {
		this.ordState = ordState;
	}
}
