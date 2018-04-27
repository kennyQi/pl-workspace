package jxc.app.service.supplier;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateSupplierPriorityCommand;
import hg.pojo.command.ModifySupplierPriorityPolicyCommand;
import hg.pojo.qo.SupplierPriorityPolicyQO;

import java.util.List;

import jxc.app.dao.supplier.SupplierPriorityPolicyDao;
import jxc.app.service.system.ProjectService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierPriorityPolicy;
import jxc.domain.model.system.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SupplierPriorityPolicyService	extends	BaseServiceImpl<SupplierPriorityPolicy, SupplierPriorityPolicyQO, SupplierPriorityPolicyDao> {

	@Autowired
	private SupplierPriorityPolicyDao supplierPriorityPolicyDao;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SupplierPriorityService supplierPriorityService;

	@Autowired
	private JxcLogger logger;

	@Override
	protected SupplierPriorityPolicyDao getDao() {
		return supplierPriorityPolicyDao;
	}

	/**
	 *	新建供应商优先级策略
	 * @param command
	 * @return
	 */
	public SupplierPriorityPolicy createSupplierPriorityPolicy(CreateSupplierPriorityCommand command) {
		
		SupplierPriorityPolicy supplierPriorityPolicy = new SupplierPriorityPolicy();
		
		StringBuffer projectName = new StringBuffer();
		StringBuffer supplierName = new StringBuffer();
		
		List<String> supplierIds = command.getSupplierIdList();
		List<String> projectIds = command.getProjectIdList();
		
		//拼接项目名称
		for (String projectId : projectIds) {
			projectName.append(" ");
			Project project = projectService.get(projectId);
			projectName.append(project.getName()).append(" ");
		}

		
		//拼接供应商名称
		for (String supplierId:supplierIds) {
			supplierName.append(" ");
			Supplier supplier = supplierService.get(supplierId);
			supplierName.append(supplier.getBaseInfo().getName()).append(" ");
		}

		
		supplierPriorityPolicy.createSupplierPriorityPolicy(projectName.toString(), supplierName.toString());
		
		this.save(supplierPriorityPolicy);
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"新增"+supplierPriorityPolicy.getProjectName()+"项目，供应商优先级策略 ",	command.getOperatorName(), command.getOperatorType(),command.getOperatorAccount(), "");
		
		return supplierPriorityPolicy;
	}

	/**
	 * 更新供应商优先级策略
	 * @param command
	 * @return
	 */
	public SupplierPriorityPolicy updateSupplierPriorityPolicy(ModifySupplierPriorityPolicyCommand command) {
		
		//获取供应商优先级策略
		SupplierPriorityPolicy supplierPriorityPolicy=this.get(command.getPolicyId());
		
		//更新供应商优先级政策
		supplierPriorityPolicy.updateSupplierPriorityPolicy(command);
		this.update(supplierPriorityPolicy);
		
		return supplierPriorityPolicy;
	}

}
