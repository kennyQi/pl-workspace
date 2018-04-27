package slfx.mp.tcclient.tc.pojo;

public class ResponseHead {
	/**
	 * 应答标识
	 */
	private String actionCode;
	/**
	 * 应答/错误类型
	 */
	private String rspType;
	/**
	 * 应答/错误代码
	 */
	private String rspCode;
	/**
	 * 应答/错误描述
	 */
	private String rspDesc;
	/**
	 * 交易流水号
	 */
	private String transactionID;
	/**
	 * 应答时间，时间格式字符串：yyyy-MM-dd HH:mm:ss.fff（落地时间）
	 */
	private String rspTime;
	/**
	 * 签名串
	 */
	private String digitalSign;
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getRspType() {
		return rspType;
	}
	public void setRspType(String rspType) {
		this.rspType = rspType;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getRspDesc() {
		return rspDesc;
	}
	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getRspTime() {
		return rspTime;
	}
	public void setRspTime(String rspTime) {
		this.rspTime = rspTime;
	}
	public String getDigitalSign() {
		return digitalSign;
	}
	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}
	
	
}
