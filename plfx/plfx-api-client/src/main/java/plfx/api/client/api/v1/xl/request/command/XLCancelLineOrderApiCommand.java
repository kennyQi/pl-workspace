package plfx.api.client.api.v1.xl.request.command;

import plfx.api.client.base.slfx.ApiPayload;

/**
 * 
 * @类功能说明：api接口取消线路订单Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午6:07:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLCancelLineOrderApiCommand extends ApiPayload {

	/**
	 * 订单编号
	 */
	private String lineOrderID;
	
	/**
	 * 取消订单的游客ID，用","隔开
	 */
	private String lineOrderTravelers;

	public String getLineOrderID() {
		return lineOrderID;
	}

	public void setLineOrderID(String lineOrderID) {
		this.lineOrderID = lineOrderID;
	}

	public String getLineOrderTravelers() {
		return lineOrderTravelers;
	}

	public void setLineOrderTravelers(String lineOrderTravelers) {
		this.lineOrderTravelers = lineOrderTravelers;
	}

	
	
	
}
