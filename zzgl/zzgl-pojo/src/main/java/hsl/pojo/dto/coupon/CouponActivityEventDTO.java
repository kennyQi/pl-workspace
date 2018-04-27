package hsl.pojo.dto.coupon;
import java.util.Date;
import hsl.pojo.dto.BaseDTO;
/**
 * @author Administrator
 * 
 */
public class CouponActivityEventDTO extends BaseDTO {

	/**
	 * @FieldsserialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发生事件
	 */
	private Date occurrenceTime;
	/**
	 * 事件类型 1：创建 2：修改基本信息 3：修改发放信息 4：修改消费信息 5：审核通过 6: 审核未通过 7：上线 8：结束 9：名额满
	 * 10：被取消
	 */
	private int eventType;
	/**
	 * 人工备注
	 */
	private String remark;
	/**
	 * 活动标识
	 */
	private String couponActivityId;
	public Date getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
}
