package hsl.pojo.command.coupon;

/**
 * @类功能说明：修改卡券活动状态command
 * @类修改者：wuyg
 * @修改日期：2014年10月15日下午2:19:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午2:19:51
 *
 */
public class ModifyCouponActivityStatusCommand {
	/**
	 * 活动编号
	 */
	private String id;
	/**
	 * 当前活动状态 1未审核 2审核未通过 3审核成功 4发放中 5名额已满 6活动结束7已取消
	 */
	private int currentValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	
}
