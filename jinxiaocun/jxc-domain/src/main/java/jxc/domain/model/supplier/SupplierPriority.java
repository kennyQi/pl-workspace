package jxc.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.M;
import jxc.domain.model.system.Project;

/**
 * 供应商优先级表
 * @author liujz
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_JXC_SUPPLIER+"SUPPLIER_PRIORITY")
public class SupplierPriority extends BaseModel {

	/**
	 * 供应商优先级策略
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPPLIER_PRIORITY_POLICY_ID")
	private SupplierPriorityPolicy policy;
	
	/**
	 * 项目id
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	/**
	 * 供应商id
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SUPPLIER_ID")
	private Supplier supplier;
	
	/**
	 * 优先级别，1为最高
	 */
	@Column(name="PRIORITY",columnDefinition=M.NUM_COLUM)
	private Integer priority;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public SupplierPriorityPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(SupplierPriorityPolicy policy) {
		this.policy = policy;
	}

	public void createSupplierPriority(SupplierPriorityPolicy policy,Integer priority,Project project,Supplier supplier) {
		setId(UUIDGenerator.getUUID());
		setPriority(priority);
		setProject(project);
		setSupplier(supplier);
		setPolicy(policy);
	}
	
	
}
