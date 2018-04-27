package plfx.jp.command.admin.gj;

import java.util.List;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：管理员申请退废票
 * @类修改者：
 * @修改日期：2015-7-20下午2:33:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午2:33:35
 */
@SuppressWarnings("serial")
public class ApplyRefundGJJPTicketCommand extends BaseCommand {

	/**
	 * 平台订单号
	 */
	private String platformOrderId;

	/**
	 * 申请退废票的乘客ID
	 */
	private List<String> passengerIds;

	/**
	 * 申请种类(一次申请只能选择一种申请理由，多个乘客可以使用同一种种类)
	 * 
	 * 1. 当日作废 2. 自愿退票 3. 非自愿退票 4. 其他
	 */
	private Integer refundType;

	/**
	 * 申请理由(200字符以内）
	 */
	private String refundMemo;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public List<String> getPassengerIds() {
		return passengerIds;
	}

	public void setPassengerIds(List<String> passengerIds) {
		this.passengerIds = passengerIds;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

}
