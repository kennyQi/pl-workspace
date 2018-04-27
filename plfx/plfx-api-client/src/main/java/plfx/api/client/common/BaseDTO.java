package plfx.api.client.common;

import java.io.Serializable;

public class BaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// ========================================================
	private String id;
	/**
	 * 结果代码：系统异常
	 */
	public final static String RESULT_ERROR = "0";
	/**
	 * 结果代码：成功
	 */
	public final static String RESULT_SUCCESS = "1";
	/**
	 * 结果代码：失败
	 */
	public final static String RESULT_FAILURE = "2";
	/**
	 * 结果代码：未通过校验
	 */
	public final static String RESULT_CHECK_FAIL = "3";
	/**
	 * 结果代码：无效经销商代码
	 */
	public final static String RESULT_DEALER_KEY_ERROR = "4";
	/**
	 * 结果代码：未授权
	 */
	public final static String RESULT_NO_AUTH = "5";

	// ========================================================
	/**
	 * 结果代码
	 */
	private String result;

	/**
	 * 结果消息
	 */
	private String message;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}