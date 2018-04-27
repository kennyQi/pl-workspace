package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBSignQueryResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2700748714182850148L;
	
	/**
	 * 标示商户交易的订单编号
	 */
	private String orderNo;
	/**
	 * 订单提交时间
	 */
	private String createTime;
	/**
	 * 交易状态 1:交易成功; 2:失败; 3：验签失败
	 */
	private String status;
	/**
	 * 交易金额
	 */
	private String amount;
	/**
	 * 订单状态
	 *0:未支付; 1:交易成功; 2:失败; 3:在途; 4:已关闭; 5:取消; 
	 * 6:待审核; 7:审核通过; 8:审核拒绝; 9:已退货; 10:可疑; 
	 * 20:已付款待发货; 30:发货待确认收货; 40：申请延期付款审核中; 
	 * 50:退款中; 51:退款成功; 52:退款失败;60:冲正撤销
	 */
	private String orderStatus;
	/**
	 * 错误信息
	 */
	private String errorMessage;
	/**
	 * 商户原订单号
	 */
	private String originalOrderNo;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getOriginalOrderNo() {
		return originalOrderNo;
	}
	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
	
}
