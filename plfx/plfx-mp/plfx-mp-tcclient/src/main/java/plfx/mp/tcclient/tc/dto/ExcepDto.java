package plfx.mp.tcclient.tc.dto;

public class ExcepDto {
	/**
	 * 异常代码
	 */
	private String code;
	/**
	 * 异常信息
	 */
	private String message;
	
	public ExcepDto(String code, String message) {
		super();
		this.code = code;
		this.message = message;
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
