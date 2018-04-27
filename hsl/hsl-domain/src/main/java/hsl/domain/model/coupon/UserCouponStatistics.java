package hsl.domain.model.coupon;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.coupon.CreateCouponCommand;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * @类功能说明：用户卡券统计表
 * @类修改者：
 * @修改日期：2015年3月5日上午11:11:09
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月5日上午11:11:09
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_COUPON + "USER_COUPON_STATISTICS")
public class UserCouponStatistics extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@Column(name="USERID",length=60)
	private String userId;
	/**
	 * 活动ID
	 */
	@Column(name="COUPONACTIVITYID",length=60)
	private String couponActivityId;
	/**
	 * 用户获得卡券的数量
	 */
	@Column(name="COUPONCOUNT",columnDefinition=M.NUM_COLUM)
	private Long couponCount;
	/**
	 * 活动卡券的时间
	 */
	@Column(name="GETCOUPONTIME",columnDefinition=M.DATE_COLUM)
	private Date getCouponTime;
	
	public void createCouponStatistic(CreateCouponCommand command){
		this.setUserId(command.getUserId());
		this.setCouponActivityId(command.getCouponActivityId());
		if(command.getPerIssueNumber()!=null){
			this.setCouponCount(command.getPerIssueNumber());
		}
		this.setId(UUIDGenerator.getUUID());
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
	public Long getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Long couponCount) {
		this.couponCount = couponCount;
	}
	public Date getGetCouponTime() {
		return getCouponTime;
	}
	public void setGetCouponTime(Date getCouponTime) {
		this.getCouponTime = getCouponTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}