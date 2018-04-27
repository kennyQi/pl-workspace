package hsl.domain.model.coupon;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.coupon.CancelCouponCommand;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.exception.CouponException;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 卡券
 *
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日上午9:13:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日上午9:13:50
 */
@Entity
@Table(name = M.TABLE_PREFIX_HSL_COUPON + "COUPON")
public class Coupon extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 卡券事件记录
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
	private List<CouponEvent> eventList;
	/**
	 * 持用用户快照
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private UserSnapshot holdingUser;
	/**
	 * 消费订单快照
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	private ConsumeOrderSnapshot consumeOrder;
	/**
	 * 卡券基本信息
	 */
	@Embedded
	private CouponBaseInfo baseInfo;
	/**
	 * 卡券状态
	 */
	@Embedded
	private CouponStatus status;

	/**
	 * 转赠次数+1
	 *
	 * @since 1.6
	 */
	public void sendTimeAdd() {
		getStatus().setAlreadySendTimes(getStatus().getAlreadySendTimes() + 1);
	}

	/**
	 * 是否可用
	 *
	 * @param orderType       订单类型 {@link hsl.domain.model.coupon.ConsumeOrderSnapshot}
	 * @param orderNo         订单号（非ID）
	 * @param orderTotalPrice 订单总价
	 * @return
	 * @since 1.7
	 */
	public boolean canUse(Integer orderType, String orderNo, Double orderTotalPrice) {

		// 检查状态是否可用
		if (CouponStatus.TYPE_NOUSED != getStatus().getCurrentValue()) {
			return false;
		}

		// 检查适用类型
		CouponUseConditionInfo useCondition = getBaseInfo().getCouponActivity().getUseConditionInfo();

		// 检查订单总价是否达到使用卡券的条件
		if (useCondition.getCondition() == 1 && orderTotalPrice < useCondition.getMinOrderPrice()) {
			return false;
		}

		Date now = new Date();
		// 检查并更正卡券状态
		if (getConsumeOrder() == null && now.after(useCondition.getEndDate())) {
			getStatus().setCurrentValue(CouponStatus.TYPE_OVERDUE);
		}
		// 检索是否在有效期
		if (now.before(useCondition.getBeginDate()) || now.after(useCondition.getEndDate())) {
			return false;
		}

		// 检查使用种类
		if (ConsumeOrderSnapshot.ORDERTYPE_JP.equals(orderType)
				|| ConsumeOrderSnapshot.ORDERTYPE_YXJP.equals(orderType)) {
			// 机票订单可用
			if (!useCondition.getUsedKind().contains(CouponUseConditionInfo.USED_KIND_JP))
				return false;
		} else if (ConsumeOrderSnapshot.ORDERTYPE_XL.equals(orderType)) {
			// 线路订单可用
			if (!useCondition.getUsedKind().contains(CouponUseConditionInfo.USED_KIND_XL))
				return false;
		}

		// 检查订单快照是否为空或已占用的订单快照就是当前订单
		if (getConsumeOrder() == null || (getConsumeOrder().getOrderType().equals(orderType) && getConsumeOrder().getOrderId().equals(orderNo))) {
			return true;
		}

		return false;
	}

	/**
	 * @throws
	 * @方法功能说明：发放卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:32:02
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public void create(CreateCouponCommand command, String userId) {
		holdingUser = new UserSnapshot();
//	   consumeOrder=new ConsumeOrderSnapshot();
		baseInfo = new CouponBaseInfo();
		status = new CouponStatus();
		setId(command.getCouponActivityId() + UUIDGenerator.getUUID());
		//设置持有者快照
		holdingUser.setId(userId);
//	   holdingUser.setLoginName(command.getLoginName());
//	   holdingUser.setRealName(command.getRealName());
//	   holdingUser.setUserId(command.getUserId());
//	   holdingUser.setEmail(command.getEmail());
//	   holdingUser.setMobile(command.getMobile());
		//设置消费订单快照
		//consumeOrder.setId(orderId);
//	   consumeOrder.setOrderId(command.getOrderId());
//	   consumeOrder.setPayPrice(command.getPayPrice());
//	   consumeOrder.setDetail(command.getDetail());
		//设置卡券基本信息
		CouponActivity c = new CouponActivity();
		c.setId(command.getCouponActivityId());
		baseInfo.setCouponActivity(c);
		baseInfo.setCreateDate(new Date());
		//来源描述
		baseInfo.setSourceDetail(command.getSourceDetail());
		//设置卡券状态
		status.setCurrentValue(CouponStatus.TYPE_NOUSED);
		//设置卡券发放事件
//	   CouponEvent couponEvent=new CouponEvent();
//	   couponEvent.setOccurrenceTime(new Date());
//	   couponEvent.setEventType(1);//设置卡券发放事件类型
//	   couponEvent.setCoupon(this);
//	   couponEvent.setId(UUIDGenerator.getUUID());
//	   eventList=new ArrayList<CouponEvent>();
//	   eventList.add(couponEvent);
	}

	/**
	 * @throws CouponException
	 * @throws
	 * @方法功能说明：消费卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:32:10
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public CouponEvent consume(ConsumeCouponCommand command) throws CouponException {
		if (status.getCurrentValue() == CouponStatus.TYPE_NOUSED) {
			status.setCurrentValue(CouponStatus.TYPE_ISUSED);

			//设置卡券消费事件
			CouponEvent couponEvent = new CouponEvent();
			couponEvent.setOccurrenceTime(new Date());
			couponEvent.setEventType(CouponEvent.TYPE_CONSUME);//设置卡券消费事件类型
			this.setId(command.getCouponId());
			couponEvent.setCoupon(this);
			couponEvent.setId(UUIDGenerator.getUUID());
			return couponEvent;
		} else {
			throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券状态异常：" + status.getCurrentValue());
		}
	}

	/**
	 * @throws CouponException 过期
	 * @throws
	 * @方法功能说明：
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:32:18
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public CouponEvent pastDue(CancelCouponCommand command) throws CouponException {
		if (status.getCurrentValue() == CouponStatus.TYPE_NOUSED) {
			status.setCurrentValue(CouponStatus.TYPE_OVERDUE);
			//设置卡券过期事件
			CouponEvent couponEvent = new CouponEvent();
			couponEvent.setOccurrenceTime(new Date());
			couponEvent.setEventType(CouponEvent.TYPE_PASTDUE);//设置卡券过期事件类型
			this.setId(command.getCouponId());
			couponEvent.setCoupon(this);
			couponEvent.setId(UUIDGenerator.getUUID());
			return couponEvent;
		} else {
			throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券状态异常：" + status.getCurrentValue());
		}
	}

	/**
	 * @throws CouponException 作废
	 * @throws
	 * @方法功能说明：
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月14日下午6:32:26
	 * @修改内容：
	 * @参数：
	 * @return:void
	 */
	public CouponEvent cancellation(CancelCouponCommand command) throws CouponException {
		if (status.getCurrentValue() == CouponStatus.TYPE_NOUSED) {
			status.setCurrentValue(CouponStatus.TYPE_CANCEL);
			//设置卡券作废事件
			CouponEvent couponEvent = new CouponEvent();
			couponEvent.setOccurrenceTime(new Date());
			couponEvent.setEventType(CouponEvent.TYPE_CANCEL);//设置卡券作废事件类型
			this.setId(command.getCouponId());
			couponEvent.setCoupon(this);
			couponEvent.setId(UUIDGenerator.getUUID());
			return couponEvent;
		} else {
			throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券状态异常：" + status.getCurrentValue());
		}
	}

	public List<CouponEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<CouponEvent> eventList) {
		this.eventList = eventList;
	}

	public UserSnapshot getHoldingUser() {
		return holdingUser;
	}

	public void setHoldingUser(UserSnapshot holdingUser) {
		this.holdingUser = holdingUser;
	}

	public ConsumeOrderSnapshot getConsumeOrder() {
		return consumeOrder;
	}

	public void setConsumeOrder(ConsumeOrderSnapshot consumeOrder) {
		this.consumeOrder = consumeOrder;
	}

	public CouponBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(CouponBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public CouponStatus getStatus() {
		if (status == null)
			status = new CouponStatus();
		return status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}

}