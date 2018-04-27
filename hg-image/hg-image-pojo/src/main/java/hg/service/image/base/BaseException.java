package hg.service.image.base;

/**
 * @类功能说明：异常错误基类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年11月3日 下午6:16:34
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {
	private int code;
	
	public BaseException() {
		super();
	}
	public BaseException(int code){
		super();
		this.code = code;
	}
	public BaseException(int code,Throwable cause){
		super(cause);
		this.code = code;	
	}
	public BaseException(int code, String message) {
		super(message);
		this.code = code;
	}
	public BaseException(int code, String message,Throwable cause) {
		super(message, cause);
		this.code = code;		
	}
	
	public int getCode() {
		return code;
	}
}