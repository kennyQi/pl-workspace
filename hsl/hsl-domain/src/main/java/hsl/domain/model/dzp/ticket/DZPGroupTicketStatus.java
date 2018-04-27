package hsl.domain.model.dzp.ticket;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * 门票状态
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPGroupTicketStatus implements HSLConstants.DZPGroupTicketStatus, HSLConstants.DZPGroupTicketRefundStatus, Serializable {
	/**
	 * 当前状态
	 * <Pre>
	 * 特别注意：这里的值代表的是直销订单的状态，和电子票务不完全一样！
	 * </Pre>
	 *
	 * @see HSLConstants.DZPGroupTicketStatus
	 */
	@Column(name = "GROUP_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer current;
	/**
	 * 退款状态
	 * <Pre>
	 * 特别注意：这里的值代表的是直销订单的状态，和电子票务不完全一样！
	 * </Pre>
	 *
	 * @see HSLConstants.DZPGroupTicketRefundStatus
	 */
	@Column(name = "REFUND_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer refundCurrent;
	/**
	 * 退款时间
	 */
	@Column(name = "REFUND_DATE", columnDefinition = M.MONEY_COLUM)
	private Date refundDate;
	/**
	 * 订单关闭时间 （超时为支付 或者 经销商取消订单）
	 */
	@Column(name = "CLOSE_DATE", columnDefinition = M.MONEY_COLUM)
	private Date closeDate;

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getRefundCurrent() {
		return refundCurrent;
	}

	public void setRefundCurrent(Integer refundCurrent) {
		this.refundCurrent = refundCurrent;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
}