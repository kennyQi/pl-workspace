package plfx.jp.pojo.exception;

@SuppressWarnings("serial")
public class BaseException extends Exception {

	private int code;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
