package slfx.mp.tcclient.tc.exception;

public class DtoErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误代码
	 */
	private String code="100002";
	/**
	 * 错误信息
	 */
	private String message="请选择正确的DTO";
	
	public DtoErrorException() {
		super();
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}