package hgfx.web.controller.api;

public class ApiResult {
	public ApiResult(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public ApiResult(int code) {
		this.code = code;
	}

	public ApiResult(){
		
	}
	/** -1,不是手机号，其他值为有效手机号 */
	private int code;
	/** 详细文本信息 */
	private String text;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}