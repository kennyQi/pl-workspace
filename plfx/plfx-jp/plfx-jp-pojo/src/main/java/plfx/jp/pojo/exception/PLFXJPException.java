package plfx.jp.pojo.exception;

/**
 * @类功能说明：票量分销机票异常
 * @类修改者：
 * @修改日期：2015-7-20下午5:36:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午5:36:57
 */
@SuppressWarnings("serial")
public class PLFXJPException extends BaseException {

	public PLFXJPException() {
		super();
	}

	public PLFXJPException(int code, String message) {
		super(code, message);
	}

	public PLFXJPException(String message) {
		super(message);
	}

}
