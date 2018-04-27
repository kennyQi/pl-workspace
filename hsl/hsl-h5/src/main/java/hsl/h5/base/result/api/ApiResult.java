package hsl.h5.base.result.api;

public class ApiResult {

	/**
	 * 返回结果代码
	 */
	private String result = RESULT_CODE_OK;
	/**
	 * 返回结果文字提示
	 */
	private String message;

	public final static String RESULT_CODE_OK = "1"; // 结果正常

	public final static String RESULT_CODE_FAIL = "-1"; // 系统错误，操作失败
	
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
