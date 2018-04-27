package hsl.domain.model.jp;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_JP_ORDER_OPERATION_LOG")
public class JPOrderOperationLog extends BaseModel {
	
	/**
	 * 操作时间
	 */
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	/**
	 * 操作类型
	 */
	@Column(name = "STATUS")
	private Integer status;
	
	/**
	 * 操作人姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	
	/**
	 * 操作信息
	 */
	@Column(name = "CONTEXT")
	private String context;
	
	/**
	 * 所属订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private JPOrder order;

	
	/////////////////////////////
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public JPOrder getOrder() {
		return order;
	}

	public void setOrder(JPOrder order) {
		this.order = order;
	}
}
