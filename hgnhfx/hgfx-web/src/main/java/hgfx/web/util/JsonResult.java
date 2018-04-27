package hgfx.web.util;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月6日 上午11:42:19
 * @版本： V1.0
 */
public class JsonResult {

	public static final int CODE_SUCCESS = 200;
	public static final int CODE_FAIL = 300;
	public static final String MSG_SUCCESS = "操作成功";
	public static final String MSG_FAIL = "操作失败";
	public static final Integer RESULT_CODE_TYPE_ONE = 1;
	public static final Integer RESULT_CODE_TYPE_TWO = 2;
	public static final Integer RESULT_CODE_TYPE_THREE = 3;
	public static final String RESULT_APPEND_SPLIT = "##";

	public JsonResult() {
	}

	public JsonResult(int code, Object msg) {
		this.code = code;
		this.msg = msg;
	}

	public static JsonResult success() {
		return new JsonResult(CODE_SUCCESS, MSG_SUCCESS);
	}

	public static JsonResult success(int code) {
		return new JsonResult(code, MSG_SUCCESS);
	}

	public static JsonResult success(Object msg) {
		return new JsonResult(CODE_SUCCESS, msg);
	}

	public static JsonResult success(int code, Object msg) {
		return new JsonResult(code, msg);
	}

	public static JsonResult fail() {
		return new JsonResult(CODE_FAIL, MSG_FAIL);
	}

	public static JsonResult fail(int code) {
		return new JsonResult(code, MSG_FAIL);
	}

	public static JsonResult fail(Object msg) {
		return new JsonResult(CODE_FAIL, msg);
	}

	public static JsonResult fail(int code, Object msg) {
		return new JsonResult(code, msg);
	}
	
	public static String dealResult(String errMsg, Integer type){
		StringBuilder sb = new StringBuilder();
		return sb.append(errMsg).append(RESULT_APPEND_SPLIT).append(type).toString();
	}

	private int code;

	private Object msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

}
