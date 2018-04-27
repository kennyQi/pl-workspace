package hg.demo.member.common.domain.result;

public class Result<T> extends BaseDomain {

    private static final long serialVersionUID = -2904652160714221234L;

    private int code = 1;//1成功； -1失败

    private String msg;

    private T result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
