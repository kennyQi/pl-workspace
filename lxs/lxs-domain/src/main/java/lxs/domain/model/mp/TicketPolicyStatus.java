package lxs.domain.model.mp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TicketPolicyStatus {

	/** 已发布
	 *  页面展示启用状态   */
	public final static Integer TICKET_POLICY_STATUS_ISSUE = 3;
	/** 已下架
	 *  页面展示禁用状态*/
	public final static Integer TICKET_POLICY_STATUS_FINISH = 4;
	
	/**
	 * 当前状态
	 */
	@Column(name = "CRRRENT")
	private Integer current = TICKET_POLICY_STATUS_FINISH;
	
	/**
	 * 是否被逻辑删除
	 */
	@Column(name = "REMOVED")
	private Boolean removed = false;

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}
	
}
