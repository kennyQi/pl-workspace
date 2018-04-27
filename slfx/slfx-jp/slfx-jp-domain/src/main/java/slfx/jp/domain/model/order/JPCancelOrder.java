
package slfx.jp.domain.model.order;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import slfx.jp.domain.model.J;
import slfx.jp.domain.model.refund.ApplyRefund;
import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：取消与退废票时新建，调完YG接口成功后保存
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月12日上午10:44:03
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "CANCEL_ORDER")
public class JPCancelOrder extends BaseModel {
	
	/**
	 * 退票
	 */
	public static final Integer JP_CANCEL_ORDER_TUIPIAO = 1;
	
	/**
	 * 废票
	 */
	public static final Integer JP_CANCEL_ORDER_FEIPIAO = 2;
	
	/**
	 * 取消
	 */
	public static final Integer JP_CANCEL_ORDER_QUXIAO = 3;
	
	/**
	 * 1 退 2 废 3取消
	 */
	@Column(name = "TYPE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer type;

	/**
	 * 关联订单 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JP_ORDER_ID")
	private JPOrder jpOrder;

	/**
	 * 乘机人
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "PASSENGER_ID")
	private Set<Passenger> passengers;

	/**
	 * 退废票原因编号
	 */
	@Column(name = "ACTION_TYPE_CODE", length = 16)
	private String actionTypeCode;

	/**
	 * 退废票原因描述、取消订单原因
	 */
	@Column(name = "ACTION_TYPE", length = 1000)
	private String actionType;

	/**
	 * 退废政策 
	 */
	@Column(name = "CANCEL_POLICY", columnDefinition = J.TEXT_COLUM)
	private String cancelPolicy;

	/**
	 * 预计退票费用
	 */
	@Column(name = "COST", columnDefinition = J.MONEY_COLUM)
	private Double cost;

	/**
	 * 7 申请废票 8 申请退款 9 退款成功 10 退废失败 15 订单取消
	 */
	@Column(name = "STATUS", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer status;

	/**
	 * 退废票记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLY_REFUND_ID")
	private ApplyRefund applyRefund;

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public JPOrder getJpOrder() {
		return jpOrder;
	}

	public void setJpOrder(JPOrder jpOrder) {
		this.jpOrder = jpOrder;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public String getCancelPolicy() {
		return cancelPolicy;
	}

	public void setCancelPolicy(String cancelPolicy) {
		this.cancelPolicy = cancelPolicy;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ApplyRefund getApplyRefund() {
		return applyRefund;
	}

	public void setApplyRefund(ApplyRefund applyRefund) {
		this.applyRefund = applyRefund;
	}

	public String getActionTypeCode() {
		return actionTypeCode;
	}

	public void setActionTypeCode(String actionTypeCode) {
		this.actionTypeCode = actionTypeCode;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

}
