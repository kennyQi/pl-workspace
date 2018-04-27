package hsl.pojo.dto.coupon;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;

/**
 * @类功能说明：卡券DTO
 * @类修改者：
 * @修改日期：2014年10月15日下午1:51:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：liusong
 * @创建时间：2014年10月15日下午1:51:58
 * 
 */
@SuppressWarnings("serial")
public class CouponEventDTO extends BaseDTO {
	private Date occurrenceTime;
	/**
	 * 时间类型 1：发放 2：消费 3：过期 4：作废 5：退还
	 */
	private int eventType;
	/**
	 * 卡券ID
	 */
	private String COUPON_ID;
	/**
	 * 快照
	 */
    private ConsumeOrderSnapshotDTO consumeOrder;

	public ConsumeOrderSnapshotDTO getConsumeOrder() {
		return consumeOrder;
	}

	public void setConsumeOrder(ConsumeOrderSnapshotDTO consumeOrder) {
		this.consumeOrder = consumeOrder;
	}

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

	public String getCOUPON_ID() {
		return COUPON_ID;
	}

	public void setCOUPON_ID(String cOUPON_ID) {
		COUPON_ID = cOUPON_ID;
	}
}
