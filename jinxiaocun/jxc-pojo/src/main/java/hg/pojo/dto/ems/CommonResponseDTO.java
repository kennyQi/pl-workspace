package hg.pojo.dto.ems;

import java.io.Serializable;

/**
 * 公共反馈结果类
 * @author liujz
 *
 */
public class CommonResponseDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 返回状态，T-成功，F-失败
	 */
	private String success;
	
	/**
	 * 反馈错误代码
	 */
	private String code;
	
	/**
	 * 返回错误描述
	 */
	private String reason;
	
	/**
	 * 仅运单轨迹回传接口用
	 */
	private String transaction_id;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	
}
