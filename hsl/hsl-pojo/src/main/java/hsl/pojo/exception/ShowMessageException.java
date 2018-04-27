package hsl.pojo.exception;

/**
 * @类功能说明：显示消息异常（仅用于提示前台的异常信息）
 * @类修改者：
 * @修改日期：2015-10-12上午9:18:04
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-12上午9:18:04
 */
@SuppressWarnings("serial")
public class ShowMessageException extends RuntimeException {
	
	public ShowMessageException() {
		super();
	}

	public ShowMessageException(String message) {
		super(message);
	}

	public static void main(String[] args) {
		throw new ShowMessageException("测试异常");
	}
}
