package plfx.mp.tcclient.tc.exception;

public class KeylessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误代码
	 */
	private String code="100001";
	/**
	 * 错误信息
	 */
	private String message="缺少API帐户密钥";
	
	public KeylessException() {
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
