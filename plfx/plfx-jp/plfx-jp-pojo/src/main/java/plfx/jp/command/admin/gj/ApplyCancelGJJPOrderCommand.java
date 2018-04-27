package plfx.jp.command.admin.gj;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：管理员取消未出票已付款的机票订单
 * @类修改者：
 * @修改日期：2015-7-20下午2:31:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午2:31:18
 */
@SuppressWarnings("serial")
public class ApplyCancelGJJPOrderCommand extends BaseCommand {

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
