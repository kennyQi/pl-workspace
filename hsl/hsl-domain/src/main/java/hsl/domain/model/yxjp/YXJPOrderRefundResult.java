package hsl.domain.model.yxjp;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.dto.yxjp.notify.RefundTicketNotify;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 退废票申请结果
 *
 * @author zhurz
 * @since 1.7
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_YJ + "REFUND_RESULT")
public class YXJPOrderRefundResult extends BaseModel {

	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private YXJPOrder fromOrder;
	/**
	 * 包含乘客
	 */
	@ManyToMany
	private List<YXJPOrderPassenger> passengers = new ArrayList<YXJPOrderPassenger>();
	/**
	 * 退票状态 1—成功 2—拒绝退废票
	 */
	@Column(name = "REFUND_STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer refundStatus;
	/**
	 * 拒绝退票理由
	 */
	@Column(name = "REFUSE_MEMO", length = 512)
	private String refuseMemo;
	/**
	 * 供应商实退金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double refundPrice;
	/**
	 * 供应商退款手续费
	 */
	@Column(name = "PROCEDURES", columnDefinition = M.MONEY_COLUM)
	private Double procedures;

	/**
	 * 是否成功
	 *
	 * @return
	 */
	public boolean success() {
		return Integer.valueOf(1).equals(getRefundStatus());
	}

	public YXJPOrder getFromOrder() {
		return fromOrder;
	}

	public void setFromOrder(YXJPOrder fromOrder) {
		this.fromOrder = fromOrder;
	}

	public List<YXJPOrderPassenger> getPassengers() {
		if (passengers == null)
			passengers = new ArrayList<YXJPOrderPassenger>();
		return passengers;
	}

	public void setPassengers(List<YXJPOrderPassenger> passengers) {
		this.passengers = passengers;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Double getProcedures() {
		return procedures;
	}

	public void setProcedures(Double procedures) {
		this.procedures = procedures;
	}

	// --------------------------------------------------------------------

	private transient Manager manager;

	public Manager manager() {
		if (manager == null)
			manager = new Manager();
		return manager;
	}

	public class Manager {

		/**
		 * 乘客是否已经全部退废成功已退款
		 *
		 * @return
		 */
		public boolean isAllRebackSuccess() {
			for (YXJPOrderPassenger passenger : getPassengers())
				if (!YXJPOrderPassengerTicket.STATUS_TICKET_REFUND_SUCC_REBACK_SUCC.equals(passenger.getTicket().getStatus()))
					return false;
			return true;
		}

		/**
		 * 创建退废票结果
		 *
		 * @param order  所属订单
		 * @param notify 退废票结果通知
		 * @return
		 */
		YXJPOrderRefundResult create(YXJPOrder order, RefundTicketNotify notify) {
			setId(UUIDGenerator.getUUID());
			setFromOrder(order);
			for (YXJPOrderPassenger passenger : order.getPassengers()) {
				if (notify.getAirIds().contains(passenger.getTicket().getTicketNo()))
					getPassengers().add(passenger);
			}
			setRefundStatus(notify.getRefundStatus());
			setRefuseMemo(notify.getRefuseMemo());
			setRefundPrice(notify.getRefundPrice());
			setProcedures(notify.getProcedures());
			return YXJPOrderRefundResult.this;
		}

	}

}
