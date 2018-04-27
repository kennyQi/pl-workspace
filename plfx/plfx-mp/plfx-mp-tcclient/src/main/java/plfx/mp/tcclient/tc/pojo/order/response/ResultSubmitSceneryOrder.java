package plfx.mp.tcclient.tc.pojo.order.response;

import plfx.mp.tcclient.tc.pojo.Result;
/**
 * 提交订单返回信息
 * @author zhangqy
 *
 */
public class ResultSubmitSceneryOrder extends Result {
	/**
	 * 订单流水号
	 */
	private String serialId;
	/**
	 * 处理时长
	 */
	private Integer mseconds;
	private String isSendSms;
	private String confirmMode;
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public Integer getMseconds() {
		return mseconds;
	}
	public void setMseconds(Integer mseconds) {
		this.mseconds = mseconds;
	}
	public String getIsSendSms() {
		return isSendSms;
	}
	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}
	public String getConfirmMode() {
		return confirmMode;
	}
	public void setConfirmMode(String confirmMode) {
		this.confirmMode = confirmMode;
	}
	
}
