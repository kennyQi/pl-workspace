package hg.dzpw.pojo.api.appv1.exception;

/**
 * @类功能说明: 参数异常
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-26 下午5:54:39
 * @版本：V1.0
 */
public class DZPWAppApiException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误编码字符
	 */
	private String code;

	/**
	 * 错误的Class标识
	 */
	private Class<?> clazz;

	public DZPWAppApiException() {
		super();
	}

	public DZPWAppApiException(String msg) {
		super(msg);
	}

	public DZPWAppApiException(Throwable cause, Class<?> clazz) {
		super(cause);
		this.clazz = clazz;
	}

	public DZPWAppApiException(String msg, Class<?> clazz) {
		super(msg);
		this.clazz = clazz;
	}

	public DZPWAppApiException(String code, String msg, Class<?> clazz) {
		super(msg);
		this.code = code;
		this.clazz = clazz;
	}

	public DZPWAppApiException(String msg, Throwable cause, Class<?> clazz) {
		super(msg, cause);
		this.clazz = clazz;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}