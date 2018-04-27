package hsl.pojo.command.yxjp.order;

import hg.common.component.BaseCommand;
import hsl.pojo.util.HSLConstants;

import java.util.List;

/**
 * 为易行机票订单付款（所有待付款的乘客）
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class PayToYXJPOrderCommand extends BaseCommand implements HSLConstants.FromType {

	/**
	 * 易行机票订单ID
	 */
	private String orderId;
	/**
	 * 来自用户
	 */
	private String fromUserId;
	/**
	 * 使用的卡券ID
	 */
	private List<String> couponIds;
	/**
	 * 来源标识：0 mobile , 1  pc
	 * 默认为PC
	 *
	 * @see HSLConstants.FromType
	 */
	private Integer fromType = FROM_TYPE_PC;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public List<String> getCouponIds() {
		return couponIds;
	}

	public void setCouponIds(List<String> couponIds) {
		this.couponIds = couponIds;
	}

	public Integer getFromType() {
		if (fromType == null)
			fromType = FROM_TYPE_PC;
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}
}
