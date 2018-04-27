package slfx.jp.pojo.dto.supplier.abe;

import slfx.jp.pojo.dto.BaseJpDTO;

@SuppressWarnings("serial")
public class ABEDeletePnrDTO extends BaseJpDTO{
	
	/**
	 * 操作状态Y:成功/N:失败
	 */
	private String status;
	
	/**
	 * 消息
	 */
	private String msg;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
