package plfx.api.client.common.exception;

/**
 * @类功能说明：接口异常
 * @类修改者：
 * @修改日期：2015-7-2下午3:36:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:36:57
 */
public class PlfxApiException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误编码字符
	 */
	private String code;

	/**
	 * 错误的Class标识
	 */
	private Class<?> clazz;

	public PlfxApiException() {
		super();
	}

	public PlfxApiException(String msg) {
		super(msg);
	}

	public PlfxApiException(Throwable cause, Class<?> clazz) {
		super(cause);
		this.clazz = clazz;
	}

	public PlfxApiException(String msg, Class<?> clazz) {
		super(msg);
		this.clazz = clazz;
	}

	public PlfxApiException(String code, String msg, Class<?> clazz) {
		super(msg);
		this.code = code;
		this.clazz = clazz;
	}

	public PlfxApiException(String msg, Throwable cause, Class<?> clazz) {
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
