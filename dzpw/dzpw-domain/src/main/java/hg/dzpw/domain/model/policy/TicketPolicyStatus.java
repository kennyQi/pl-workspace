package hg.dzpw.domain.model.policy;

import hg.dzpw.domain.model.M;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

/**
 * @类功能说明: 联票政策状态
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:18:20
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicyStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 未审核   已停用 */
	@Deprecated
	public final static Integer TICKET_POLICY_STATUS_UNCHECK = 0;
	/** 未通过   已停用*/
	@Deprecated
	public final static Integer TICKET_POLICY_STATUS_DISAPPROVED = 1;
	/** 已审核   已停用*/
	@Deprecated
	public final static Integer TICKET_POLICY_STATUS_CHECKED = 2;
	
	/** 已发布
	 *  页面展示启用状态   */
	public final static Integer TICKET_POLICY_STATUS_ISSUE = 3;
	/** 已下架
	 *  页面展示禁用状态*/
	public final static Integer TICKET_POLICY_STATUS_FINISH = 4;

	/**
	 * 当前状态
	 */
	@Column(name = "CURRENT", columnDefinition = M.NUM_COLUM)
	private Integer current = TICKET_POLICY_STATUS_FINISH;

	/**
	 * 是否被逻辑删除
	 */
	@Type(type = "yes_no")
	@Column(name = "REMOVED")
	private Boolean removed = false;
	
	/**
	 * @方法功能说明：检查状态是否为空，为空便设置默认值
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-5下午3:30:49
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	private void checkNull() {
		if (current == null)
			current = TICKET_POLICY_STATUS_FINISH;
	}


	/**
	 * @方法功能说明: 是否已发布
	 */
	public boolean isIssue() {
		checkNull();
		if (current.intValue() == TICKET_POLICY_STATUS_ISSUE.intValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @方法功能说明: 是否已下架
	 */
	public boolean isFinish() {
		checkNull();
		if (current.intValue() == TICKET_POLICY_STATUS_FINISH.intValue()) {
			return true;
		} else {
			return false;
		}
	}

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