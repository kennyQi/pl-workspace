package hsl.domain.model.coupon;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

/**
 * 卡券事件记录
 *
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午8:41:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午8:41:52
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_COUPON + "COUPON_EVENT")
public class CouponEvent extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 发生时间
	 */
	@Column(name = "OCCURRENCETIME", columnDefinition = M.DATE_COLUM)
	private Date occurrenceTime;
	/**
	 * 发放
	 */
	public final static int TYPE_SEND = 1;
	/**
	 * 消费
	 */
	public final static int TYPE_CONSUME = 2;
	/**
	 * 过期
	 */
	public final static int TYPE_PASTDUE = 3;
	/**
	 * 作废
	 */
	public final static int TYPE_CANCEL = 4;
	/**
	 * 退还
	 */
	public final static int TYPE_RETURN = 5;
	/**
	 * 赠送
	 */
	public final static int TYPE_PRESENT = 6;
	/**
	 * 事件类型
	 * 1：发放
	 * 2：消费
	 * 3：过期
	 * 4：作废
	 * 5：退还
	 * 6：赠送
	 */
	@Column(name = "EVENTTYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer eventType;
	/**
	 * 卡券ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUPON_ID")
	private Coupon coupon;
	/**
	 * 快照id
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	private ConsumeOrderSnapshot consumeOrder;

	public Date getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Date occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public ConsumeOrderSnapshot getConsumeOrder() {
		return consumeOrder;
	}

	public void setConsumeOrder(ConsumeOrderSnapshot consumeOrder) {
		this.consumeOrder = consumeOrder;
	}

}