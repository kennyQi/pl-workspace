package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientCommand;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

/**
 * @类功能说明：确认支付
 * @类修改者：
 * @修改日期：2015-4-22下午4:58:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-22下午4:58:21
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.PayToTicketOrder)
public class PayToTicketOrderCommand extends BaseClientCommand {

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderId;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

}
