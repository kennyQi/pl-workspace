package plfx.api.client.base.slfx;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ApiResponse implements Serializable {

	/**
	 * 返回结果代码
	 */
	private String result = RESULT_CODE_OK;
	/**
	 * 返回结果文字提示
	 */
	private String message;

	public final static String RESULT_CODE_OK = "1"; // 结果正常
	public final static String RESULT_CODE_FAILE="-1";//结果异常
			
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

	public static String getResultCodeOk() {
		return RESULT_CODE_OK;
	}

}
