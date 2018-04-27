package hg.dzpw.pojo.common;

@SuppressWarnings("serial")
public class BaseException extends Exception {
	
	private int code;
	
	public BaseException() {
		super();
	}
	
	public BaseException(int code,Throwable cause){
		super(cause);
		this.code = code;	
	}
	
	public BaseException(int code, String message) {
		super(message);
		this.code = code;		
	}
	
	public BaseException(int code, String message,Throwable cause) {
		super(message, cause);
		this.code = code;		
	}
	
	public int getCode() {
		return code;
	}
}