package slfx.mp.tcclient.tc.pojo;

import slfx.mp.tcclient.tc.dto.ExcepDto;

/**
 * 调用同城接口返回相应信息
 * @author zhangqy
 *
 */
public class Result {
	/**
	 * 返回结果代码
	 */
	private String resultCode;
	/**
	 * 返回结果信息
	 */
	private String resultMessage;
	/**
	 * 子类覆盖,用于异常调用
	 * @param param
	 * @param dto
	 * @throws Exception
	 */
	public void setResult(Result param,ExcepDto dto) {
		this.resultCode=dto.getCode();
		this.resultMessage=dto.getMessage();
	}
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
}
