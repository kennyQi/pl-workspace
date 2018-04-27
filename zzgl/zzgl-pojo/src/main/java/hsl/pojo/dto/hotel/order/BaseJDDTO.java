package hsl.pojo.dto.hotel.order;

import hsl.pojo.dto.BaseDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：机票baseDTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午5:01:39
 * @版本：V1.0
 *
 */
public class BaseJDDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	/**
	 * 调用接口名
	 * @FieldsinterfaceName:TODO
	 */
	public String interfaceName;
	
	/**
	 * 调用时间
	 * @FieldscallTime:TODO
	 */
	public Date callTime;
	
	/**
	 * 错误代码   
	 * 0  表示成功
	 * 999999 自定义错误代码
	 * @FieldserrorCode:TODO
	 */
	public String errorCode;
	
	/**
	 * 错误消息、描述
	 * @FieldserrorMsg:TODO
	 */
	public String errorMsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
