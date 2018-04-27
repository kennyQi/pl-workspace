package hsl.pojo.command.yxjp.order;

import hg.common.component.BaseCommand;
import hsl.pojo.util.HSLConstants;

import java.util.List;

/**
 * 申请退废票（管理员操作，必须附带fromAdminId）
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class ApplyRefundYXJPOrderCommand extends BaseCommand {

	/**
	 * 订单ID
	 */
	private String orderId;

	/**
	 * 乘客ID
	 */
	private List<String> passengerIds;

	/**
	 * 申请种类
	 *
	 * @see HSLConstants.YXJPOrderRefundApply
	 */
	private Integer refundType;

	/**
	 * 申请理由
	 */
	private String refundMemo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
		if (refundMemo == null)
			refundMemo = "";
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}
}
