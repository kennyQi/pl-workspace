package hsl.pojo.dto.hotel.base;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:45:57
 * @版本：V1.0
 * 
 */
public class BaseRatePlanDTO {

	/**
	 * 产品编号
	 */
	protected int ratePlanId;
	/**
	 * 产品名称
	 */
	protected String ratePlanName;
	/**
	 * 预订最少数量
	 */
	protected int minAmount;
	/**
	 * 最少入住天数
	 */
	protected int minDays;
	/**
	 * 最多入住天数
	 */
	protected int maxDays;
	/**
	 * 付款类型（里面是枚举）
	 */
	protected String paymentType;

	public int getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(int ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	public int getMinDays() {
		return minDays;
	}

	public void setMinDays(int minDays) {
		this.minDays = minDays;
	}

	public int getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
