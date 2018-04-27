package slfx.mp.tcclient.tc.pojo.order.response;

import slfx.mp.tcclient.tc.pojo.Result;
/**
 * 取消订单返回信息
 * @author zhangqy
 *
 */
public class ResultCancelSceneryOrder extends Result {
	/**
	 * 是否成功
	 */
	private Integer isSuc;
	/**
	 * 取消失败原因
	 */
	private String errMsg;
	public Integer getIsSuc() {
		return isSuc;
	}
	public void setIsSuc(Integer isSuc) {
		this.isSuc = isSuc;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
