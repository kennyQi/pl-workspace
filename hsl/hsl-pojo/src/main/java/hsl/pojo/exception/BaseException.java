package hsl.pojo.exception;

@SuppressWarnings("serial")
public class BaseException extends Exception {
	
	private Integer code;
	
	public BaseException() {
		super();
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;		
	}

	public Integer getCode() {
		return code;
	}
	
}
