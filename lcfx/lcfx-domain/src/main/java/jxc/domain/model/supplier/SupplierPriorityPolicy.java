package jxc.domain.model.supplier;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.ModifySupplierPriorityPolicyCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import jxc.domain.model.M;

/**
 * 供应商优先级策略
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SUPPLIER + "SUPPLIER_PRIORITY_POLICY")
public class SupplierPriorityPolicy extends BaseModel {

	/**
	 * 项目名称
	 */
	@Column(name = "PROJECT_NAME", columnDefinition = M.TEXT_COLUM)
	private String projectName;

	/**
	 * 供应商名称
	 */
	@Column(name = "SUPPLIER_NAME", columnDefinition = M.TEXT_COLUM)
	private String supplierName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void createSupplierPriorityPolicy(String projectName, String supplierName) {
		setId(UUIDGenerator.getUUID());
		setProjectName(projectName);
		setSupplierName(supplierName);
	}

	public void updateSupplierPriorityPolicy(ModifySupplierPriorityPolicyCommand command) {

		if (StringUtils.isNotBlank(command.getProjectName())) {
			setProjectName(command.getProjectName());
		}

		if (StringUtils.isNotBlank(command.getSupplierName())) {
			setSupplierName(command.getSupplierName());
		}
	}
}
