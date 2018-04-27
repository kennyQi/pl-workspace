package pl.cms.pojo.exception;

@SuppressWarnings("serial")
public class BaseRuntimeException extends RuntimeException {
	
	private int code;
	
	public BaseRuntimeException() {
		super();
	}
	
	public BaseRuntimeException(int code, String message) {
		super(message);
		this.code = code;		
	}

	public int getCode() {
		return code;
	}
	
}
