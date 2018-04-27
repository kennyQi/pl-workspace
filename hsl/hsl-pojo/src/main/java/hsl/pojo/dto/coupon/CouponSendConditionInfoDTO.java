package hsl.pojo.dto.coupon;
import java.util.Date;
/**
 * @类功能说明：卡券赠送条件
 * @类修改者：
 * @修改日期：2015年3月4日上午11:21:44
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月4日上午11:21:44
 */
public class CouponSendConditionInfoDTO {
	/**
	 * 是否可以赠送
	 */
	private Boolean isSend;
	/**
	 * 赠送奖励卡券ID（多个ID用,号 分割）
	 */
	private String sendAppendCouponIds;
	/**
	 * 赠送用户的符合的日期
	 */
	private Date userCreateTime;
	/**
	 * 赠送次数
	 */
	private Integer sendTimes;
	public Boolean getIsSend() {
		return isSend;
	}
	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}
	public String getSendAppendCouponIds() {
		return sendAppendCouponIds;
	}
	public void setSendAppendCouponIds(String sendAppendCouponIds) {
		this.sendAppendCouponIds = sendAppendCouponIds;
	}
	public Date getUserCreateTime() {
		return userCreateTime;
	}
	public void setUserCreateTime(Date userCreateTime) {
		this.userCreateTime = userCreateTime;
	}
	public Integer getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}
}
