package hsl.pojo.exception;

/**
 * @类功能说明： 线路销售方案的异常信息类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/2 10:24
 */
public class LSPException extends BaseException {
	public LSPException(String message) {
		super(message);
	}
	public LSPException(Integer code, String message) {
		super(code, message);
	}
}
