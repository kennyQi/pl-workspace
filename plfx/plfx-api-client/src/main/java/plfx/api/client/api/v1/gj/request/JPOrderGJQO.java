package plfx.api.client.api.v1.gj.request;

import java.util.List;

import plfx.api.client.common.BaseClientQO;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：国际机票订单查询对象
 * @类修改者：
 * @修改日期：2015-7-8下午5:47:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午5:47:35
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_QueryJPOrder)
public class JPOrderGJQO extends BaseClientQO {
	
	// 不可都为空

	/**
	 * 分销商订单ID
	 */
	private List<String> platformOrderIds;

	/**
	 * 经销商订单ID
	 */
	private List<String> dealerOrderIds;

	public List<String> getPlatformOrderIds() {
		return platformOrderIds;
	}

	public void setPlatformOrderIds(List<String> platformOrderIds) {
		this.platformOrderIds = platformOrderIds;
	}

	public List<String> getDealerOrderIds() {
		return dealerOrderIds;
	}

	public void setDealerOrderIds(List<String> dealerOrderIds) {
		this.dealerOrderIds = dealerOrderIds;
	}

}
