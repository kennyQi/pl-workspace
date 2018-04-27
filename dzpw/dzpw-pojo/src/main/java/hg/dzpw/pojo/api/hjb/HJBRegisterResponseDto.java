package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBRegisterResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 49541759244242553L;
	/**
	 * 交易状态 0、成功1、失败
	 */
	private String status;
	/**
	 * 返回错误码，为空时，无错误 1、数据不合法 2、用户已注册
	 */
	private String errorCode;
	/**
	 * 错误信息
	 */
	private String errorMessage;
	/**
	 * 汇金宝平台返回的企业用户编号
	 */
	private String cstNo;
	/**
	 * 汇金宝平台返回的操作员编号
	 */
	private String operatorNo;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getCstNo() {
		return cstNo;
	}
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
	public String getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	
}
