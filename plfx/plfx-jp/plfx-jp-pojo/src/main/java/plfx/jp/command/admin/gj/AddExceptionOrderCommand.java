package plfx.jp.command.admin.gj;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：管理员新增异常订单
 * @类修改者：
 * @修改日期：2015-7-20下午2:39:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午2:39:29
 */
@SuppressWarnings("serial")
public class AddExceptionOrderCommand extends BaseCommand {

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 异常订单调整 金额
	 */
	private Double adjustAmount = 0d;

	/**
	 * 异常订单调整原因
	 */
	private String adjustReason;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

}
