package hsl.domain.model.coupon;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
/***********************************************************************
 * Module:  CouponActivityEvent.java
 * Author:  yuxx
 * Purpose: Defines the Class CouponActivityEvent
 ***********************************************************************/
/**
 * 卡券活动事件
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:26:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:26:10
 *
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_COUPON+"COUPONACTIVITY_EVENT")
public class CouponActivityEvent extends BaseModel{
	private static final long serialVersionUID = 1L;
  /**
   * 发生事件
   */
   @Column(name="OCCURRENCETIME", columnDefinition=M.DATE_COLUM)
   private Date occurrenceTime;
   /**
    * 事件类型
    * 1：创建
    * 2：修改基本信息
    * 3：修改发放信息
    * 4：修改消费信息
    * 5：审核通过
    * 6: 审核未通过
    * 7：上线
    * 8：结束
    * 9：名额满
    * 10：被取消 
    */
    @Column(name="EVENTTYPE" ,columnDefinition=M.TYPE_NUM_COLUM)
    private Integer eventType;
    /**
     * 人工备注
     */
    @Column(name="REMARK", columnDefinition=M.TEXT_COLUM)
    private String remark;
    /**
     * 活动标识
     */
    @Column(name="NAME", length = 64)
    private String couponActivityId;
    
  //卡券活动事件
  	/**
  	 * 1：创建
  	 */
  	public static final Integer EVENT_TYPE_CREATE=1;
  	/**
  	 * 2：修改基本信息
  	 */
  	public static final Integer EVENT_TYPE_MODIFY_BASEINFO=2;
  	/**
  	 * 3：修改发放信息
  	 */
  	public static final Integer EVENT_TYPE_MODIFY_ISSUEINFO=3;
  	/**
  	 * 4：修改消费信息
  	 */
  	public static final Integer EVENT_TYPE_MODIFY_CONSUMEINFO=4;
  	/**
  	 * 5：审核通过
  	 */
  	public static final Integer EVENT_TYPE_CHECK_OK=5;
  	/**
  	 * 6: 审核未通过
  	 */
  	public static final Integer EVENT_TYPE_CHECK_FAIL=6;
  	/**
  	 * 7：上线
  	 */
  	public static final Integer EVENT_TYPE_ONLINE=7;
  	/**
  	 * 8：结束
  	 */
  	public static final Integer EVENT_TYPE_OVER=8;
  	/**
  	 * 9：名额满
  	 */
  	public static final Integer EVENT_TYPE_QUTOA_FULL=9;
  	/**
  	 * 10：被取消
  	 */
  	public static final Integer EVENT_TYPE_CANCELED=10;
  	
  	@SuppressWarnings("serial")
  	public static final Map<String, String> EVENTMAP=new HashMap<String, String>(){{
  		put("1", "创建");
  		put("2", "修改基本信息");
  		put("3", "修改发放信息");
  		put("4", "修改消费信息");
  		put("5", "审核通过");
  		put("6", "审核未通过");
  		put("7", "上线");
  		put("8", "结束");
  		put("9", "名额满");
  		put("10", "被取消");
  	}};
  	
  	
	public java.util.Date getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(java.util.Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
	public java.lang.Integer getEventType() {
		return eventType;
	}
	public void setEventType(java.lang.Integer eventType) {
		this.eventType = eventType;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getCouponActivityId() {
		return couponActivityId;
	}
	public void setCouponActivityId(java.lang.String couponActivityId) {
		this.couponActivityId = couponActivityId;
	}
	public CouponActivityEvent() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 普通事件的带参构造函数
	 * @类名：CouponActivityEvent.java
	 * @描述：TODO
	 * @@param eventtype
	 * @@param couponActivityId
	 */
	public CouponActivityEvent(int eventtype,String couponActivityId) {
		this.couponActivityId=couponActivityId;
		this.eventType=eventtype;
		this.occurrenceTime=new Date();
		setId(UUIDGenerator.getUUID());
	}
	/**
	 * 取消事件的构造函数
	 * @类名：CouponActivityEvent.java
	 * @描述：TODO
	 * @@param eventtype
	 * @@param couponActivityId
	 * @@param remark
	 */
	public CouponActivityEvent(int eventtype,String couponActivityId,String remark) {
		this.couponActivityId=couponActivityId;
		this.eventType=eventtype;
		this.occurrenceTime=new Date();
		this.remark=remark;
		setId(UUIDGenerator.getUUID());
	}
}