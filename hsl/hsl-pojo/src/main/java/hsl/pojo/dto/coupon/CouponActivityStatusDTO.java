package hsl.pojo.dto.coupon;

/**
 * @author Administrator
 *
 */
public class CouponActivityStatusDTO {
	/**
	 * 当前活动状态
	 * 1未审核 2审核未通过 3审核成功 4发放中 5名额已满 6活动结束7已取消
	 */
    private int currentValue;
    /**
     * 已发放数量
     */
	 private long issueNum;
	public int getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	public long getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(long issueNum) {
		this.issueNum = issueNum;
	}
}
