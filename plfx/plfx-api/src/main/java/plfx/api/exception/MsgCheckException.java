package plfx.api.exception;

/**
 * @类功能说明：校验异常
 * @类修改者：
 * @修改日期：2014-12-3下午2:14:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-3下午2:14:50
 */
public class MsgCheckException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MsgCheckException() {
		super();
	}

	public MsgCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public MsgCheckException(String message) {
		super(message);
	}

	public MsgCheckException(Throwable cause) {
		super(cause);
	}
	
}