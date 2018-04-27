package slfx.jp.pojo.exception;

/**
 * 
 * @类功能说明：异常基础类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午3:22:00
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {
	
	private String code;
	
	public BaseException() {
		super();
	}

	public BaseException(String code, String message) {
		super(message);
		this.code = code;		
	}

	public String getCode() {
		return code;
	}
	
}
