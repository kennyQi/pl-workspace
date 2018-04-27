package plfx.mp.spi.exception;

import static plfx.mp.spi.common.MpEnumConstants.ExceptionCode.MESSAGE_MAP;

public class SlfxMpException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	
	public SlfxMpException(String code) {
		this(code, MESSAGE_MAP.get(code));
	}

	public SlfxMpException(String code, String message) {
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
