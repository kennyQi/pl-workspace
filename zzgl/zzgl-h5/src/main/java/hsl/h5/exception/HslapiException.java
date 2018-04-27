package hsl.h5.exception;

public class HslapiException extends Exception {

	private static final long serialVersionUID = 1L;

	public HslapiException() {
		super();
	}

	public HslapiException(String message, Throwable cause) {
		super(message, cause);
	}

	public HslapiException(String message) {
		super(message);
	}

	public HslapiException(Throwable cause) {
		super(cause);
	}
	
}
