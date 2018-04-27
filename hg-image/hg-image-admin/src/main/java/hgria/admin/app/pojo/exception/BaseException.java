package hgria.admin.app.pojo.exception;

@SuppressWarnings("serial")
public class BaseException extends Exception {
	
	private int code;
	
	public BaseException() {
		super();
	}
	
	public BaseException(int code, String message) {
		super(message);
		this.code = code;		
	}

	public int getCode() {
		return code;
	}
	
}
