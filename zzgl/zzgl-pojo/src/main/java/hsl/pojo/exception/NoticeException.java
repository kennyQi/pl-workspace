package hsl.pojo.exception;
/**
 * @类功能说明：公告异常类
 * @类修改者：
 * @修改日期：2014年12月15日下午1:33:30
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月15日下午1:33:30
 *
 */
public class NoticeException extends BaseException{
	private static final long serialVersionUID = 1L;

	public NoticeException(Integer code, String message) {
		super(code, message);
	}
	public NoticeException(String message) {
		super(message);
	}
}
