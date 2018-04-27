package hsl.pojo.command.coupon;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：审核卡券活动
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年10月22日下午4:15:02
 * 
 */
@SuppressWarnings("serial")
public class CheckCouponActivityCommand extends BaseCommand {

	private String couponActivityId;
	/**
	 * 是否批准
	 */
	private boolean approved;

	public boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getCouponActivityId() {
		return couponActivityId;
	}

	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}

}
