package hsl.domain.model.coupon;
import hsl.domain.model.M;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;
/**
 * @类功能说明：卡券活动赠送条件
 * @类修改者：
 * @修改日期：2015年3月4日上午11:20:49
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月4日上午11:20:49
 */
@Embeddable
public class CouponSendConditionInfo {
	/**
	 * 是否可以赠送
	 */
	@Column(name="ISSEND")
	@Type(type = "yes_no")
	private Boolean isSend;
	/**
	 * 赠送奖励卡券ID（多个ID用,号 分割）
	 */
	@Column(name="SENDCOUPONIDS",length=200)
	private String sendAppendCouponIds;
	/**
	 * 赠送用户的符合的日期
	 */
	@Column(name="USERCREATETIME",columnDefinition=M.DATE_COLUM)
	private Date userCreateTime;
	
	public Boolean getIsSend() {
		return isSend;
	} 
	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}
	public Date getUserCreateTime() {
		return userCreateTime;
	}
	public void setUserCreateTime(Date userCreateTime) {
		this.userCreateTime = userCreateTime;
	}
	public String getSendAppendCouponIds() {
		return sendAppendCouponIds;
	}
	public void setSendAppendCouponIds(String sendAppendCouponIds) {
		this.sendAppendCouponIds = sendAppendCouponIds;
	}
}