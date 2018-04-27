package hsl.pojo.dto.coupon;

import hsl.pojo.dto.BaseDTO;

import java.util.List;

/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午1:51:58
 * 
 */
@SuppressWarnings("serial")
public class CouponDTO extends BaseDTO {

	/**
	 * 持用用户快照
	 */
	private UserSnapshotDTO holdingUser;

	/**
	 * 消费订单快照
	 */
	private ConsumeOrderSnapshotDTO consumeOrder;
	/**
	 * 卡券基本信息
	 */
	private CouponBaseInfoDTO baseInfo;
	/**
	 * 卡券状态
	 */
	private CouponStatusDTO status;

	private List<CouponEventDTO> eventList;

	public UserSnapshotDTO getHoldingUser() {
		return holdingUser;
	}

	public void setHoldingUser(UserSnapshotDTO holdingUser) {
		this.holdingUser = holdingUser;
	}

	public ConsumeOrderSnapshotDTO getConsumeOrder() {
		return consumeOrder;
	}

	public void setConsumeOrder(ConsumeOrderSnapshotDTO consumeOrder) {
		this.consumeOrder = consumeOrder;
	}

	public CouponStatusDTO getStatus() {
		return status;
	}

	public void setStatus(CouponStatusDTO status) {
		this.status = status;
	}

	public List<CouponEventDTO> getEventList() {
		return eventList;
	}

	public void setEventList(List<CouponEventDTO> eventList) {
		this.eventList = eventList;
	}

	public CouponBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(CouponBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}
}
