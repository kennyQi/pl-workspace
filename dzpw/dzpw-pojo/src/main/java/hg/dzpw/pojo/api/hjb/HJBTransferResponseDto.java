package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBTransferResponseDto implements Serializable {


	private static final long serialVersionUID = 10223592543756886L;

	/**
	 * 交易状态 1:交易成功; 2:失败
	 */
	private String status;
	/**
	 * 汇金宝交易订单编号
	 */
	private String orderNo;
	/**
	 * 交易金额 单位：分
	 */
	private String orderAmt;
	/**
	 * 错误代码
	 */
	private String errorCode;
	/**
	 * 交易信息
	 */
	private String message;
	/**
	 * 接入商城的订单号
	 */
	private String originalOrderNo;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getErrCode() {
		return errorCode;
	}
	public void setErrCode(String errCode) {
		this.errorCode = errCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOriginalOrderNo() {
		return originalOrderNo;
	}
	public void setOriginalOrderNo(String originalOrderNo) {
		this.originalOrderNo = originalOrderNo;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	
}
