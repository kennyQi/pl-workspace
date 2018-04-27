package pay.record.api.client.common.exception;

/**
 * 
 * @类功能说明：接口异常
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月23日下午2:58:17
 * @版本：V1.0
 *
 */
public class PayRecordApiException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 错误编码字符
	 */
	private String code;

	/**
	 * 错误的Class标识
	 */
	private Class<?> clazz;

	public PayRecordApiException() {
		super();
	}

	public PayRecordApiException(String msg) {
		super(msg);
	}

	public PayRecordApiException(Throwable cause, Class<?> clazz) {
		super(cause);
		this.clazz = clazz;
	}

	public PayRecordApiException(String msg, Class<?> clazz) {
		super(msg);
		this.clazz = clazz;
	}

	public PayRecordApiException(String code, String msg, Class<?> clazz) {
		super(msg);
		this.code = code;
		this.clazz = clazz;
	}

	public PayRecordApiException(String msg, Throwable cause, Class<?> clazz) {
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
