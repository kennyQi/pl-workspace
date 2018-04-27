package zzpl.domain.model.workflow;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

/**
 * 
 * @类功能说明：流程实例所关联的订单表
 * @类修改者：
 * @修改日期：2015年7月1日下午2:05:17
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月1日下午2:05:17
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_WORKFLOW + "WORKFLOW_INSTANCE_ORDER")
public class WorkflowInstanceOrder extends BaseModel {

	public final static String GN_FLIGHT_ORDER = "1";
	
	public final static String GJ_FLIGHT_ORDER = "2";
	
	/**
	 * 流程ID
	 */
	@ManyToOne
	@JoinColumn(name = "WORKFLOW_INSTANCE_ID")
	private WorkflowInstance workflowInstance;

	/**
	 * 订单类型 
	 * 1：国内机票
	 * 2：国际机票
	 */
	@Column(name = "ORDER_TYPE", length = 512)
	private String orderType;

	/**
	 * 订单ID
	 */
	@Column(name = "ORDER_ID", length = 512)
	private String orderID;

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(WorkflowInstance workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

}
