package lxs.api.base;

public class ApiResponse {

	/**
	 * 返回结果代码
	 */
	private Integer result = RESULT_CODE_OK;
	/**
	 * 返回结果文字提示
	 */
	private String message;

	/**
	 * 请求的sessionId
	 */
	private String sessionId;

	public final static Integer RESULT_CODE_OK = 1; // 结果正常
	public final static Integer RESULT_CODE_FAIL = -1; // 系统错误，操作失败
		
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
