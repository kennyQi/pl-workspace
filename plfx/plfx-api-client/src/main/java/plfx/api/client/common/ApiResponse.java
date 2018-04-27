package plfx.api.client.common;

import java.io.Serializable;

/**
 * @类功能说明：API返回
 * @类修改者：
 * @修改日期：2014-11-18下午2:21:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午2:21:27
 */
public class ApiResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	// ========================================================
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

	public ApiResponse() {
	}

	public ApiResponse(String result, String message) {
		this.result = result;
		this.message = message;
	}

	/**
	 * @方法功能说明：检查异常
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-4上午10:28:12
	 * @修改内容：
	 * @参数：@param msg
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	public static ApiResponse checkFail(String msg) {
		return new ApiResponse(RESULT_CHECK_FAIL, msg);
	}

	/**
	 * @方法功能说明：其他异常
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-4上午10:27:56
	 * @修改内容：
	 * @参数：@param msg
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	public static ApiResponse error(String msg) {
		return new ApiResponse(RESULT_ERROR, msg);
	}

	/**
	 * @方法功能说明：接口返回异常
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-4上午10:27:40
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@param msg
	 * @参数：@return
	 * @return:ApiResponse
	 * @throws
	 */
	public static ApiResponse fail(String code, String msg) {
		return new ApiResponse(code, msg);
	}
	
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
}