package plfx.api.client.api.v1.gj.request;

import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：申请取消已付款未出票的订单
 * @类修改者：
 * @修改日期：2015-7-7下午3:16:38
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:16:38
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_ApplyCancelOrder)
public class ApplyCancelOrderGJCommand extends BaseClientCommand {

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

}
