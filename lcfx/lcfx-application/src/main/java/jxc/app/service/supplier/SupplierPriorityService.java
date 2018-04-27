package jxc.app.service.supplier;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateSupplierPriorityCommand;
import hg.pojo.command.DeleteSupplierPriorityCommand;
import hg.pojo.command.ModifySupplierPriorityCommand;
import hg.pojo.command.ModifySupplierPriorityPolicyCommand;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.SupplierPriorityQO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxc.app.dao.supplier.SupplierPriorityDao;
import jxc.app.service.system.ProjectService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.supplier.SupplierPriority;
import jxc.domain.model.supplier.SupplierPriorityPolicy;
import jxc.domain.model.system.Project;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SupplierPriorityService extends BaseServiceImpl<SupplierPriority, SupplierPriorityQO, SupplierPriorityDao> {

	@Autowired
	SupplierPriorityPolicyService priorityPolicyService;
	
	@Autowired
	private SupplierPriorityDao supplierPriorityDao;

	@Autowired
	private SupplierPriorityPolicyService supplierPriorityPolicyService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private JxcLogger logger;

	@Override
	protected SupplierPriorityDao getDao() {
		return supplierPriorityDao;
	}

	/**
	 * 新建供应商优先级
	 * @param command
	 */
	public void createSupplierPriority(CreateSupplierPriorityCommand command) {
		
		//创建供应商优先级策略
		SupplierPriorityPolicy policy = priorityPolicyService.createSupplierPriorityPolicy(command);
		
		//获取供应商id、项目id及优先级列表
		List<String> projectIds = command.getProjectIdList();
		List<String> supplierIds = command.getSupplierIdList();
		List<Integer> prioritys = command.getPriorityList();
		
		for (String projectId : projectIds) {
			
			Project project = projectService.get(projectId);
			
			for (int i = 0; i < supplierIds.size(); i++) {
				
				Supplier supplier = supplierService.get(supplierIds.get(i));
				Integer priority = prioritys.get(i);
				
				SupplierPriority supplierPriority = new SupplierPriority();				
				supplierPriority.createSupplierPriority(policy, priority, project, supplier);
				this.save(supplierPriority);
				
				logger.debug(this.getClass(), "czh", command.getOperatorName()+"新增"+project.getName()+"项目，供应商" + supplier.getBaseInfo().getName()+"优先级，优先级别为"+priority, command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
			}
		}
	}

	/**
	 * 更新供应商优先级
	 * @param command
	 */
	public void updateSupplierPriority(ModifySupplierPriorityCommand command) {
		
		//删除供应商优先级策略及其列表
		DeleteSupplierPriorityCommand deleteSupplierPriorityCommand=new DeleteSupplierPriorityCommand();
		deleteSupplierPriorityCommand.setSupplierPriorityPolicyId(command.getSupplierPriorityPolicyId());
		deleteSupplierPriorityCommand.setOperatorAccount(command.getOperatorAccount());
		deleteSupplierPriorityCommand.setOperatorName(command.getOperatorName());
		deleteSupplierPriorityCommand.setOperatorType(command.getOperatorType());
		this.deleteSupplierPriority(deleteSupplierPriorityCommand);

		//创建新的供应商优先级策略及其列表
		CreateSupplierPriorityCommand createSupplierPriorityCommand =new CreateSupplierPriorityCommand();
		createSupplierPriorityCommand.setProjectIdList(command.getProjectIdList());
		createSupplierPriorityCommand.setSupplierIdList(command.getSupplierIdList());
		createSupplierPriorityCommand.setPriorityList(command.getPriorityList());
		createSupplierPriorityCommand.setOperatorAccount(command.getOperatorAccount());
		createSupplierPriorityCommand.setOperatorName(command.getOperatorName());
		createSupplierPriorityCommand.setOperatorType(command.getOperatorType());
		this.createSupplierPriority(createSupplierPriorityCommand);
	}

	/**
	 * 删除供应商优先级
	 * @param command
	 */
	public void deleteSupplierPriority(DeleteSupplierPriorityCommand command) {
		
		//判断根据哪种条件删除优先级	
		if(StringUtils.isNotBlank(command.getSupplierPriorityPolicyId())){
			
			//根据优先级策略，删除供应商优先级及策略
			this.deleteSupplierPriorityByPolicyId(command);
			
		}else{
			
			//根据供应商id列表，删除供应商优先级及修改策略
			this.deleteSupplierPriorityBySupplierIds(command);
		}

	}
	
	
	/**
	 * 根据优先级策略，删除供应商优先级
	 * @param command
	 */
	public void deleteSupplierPriorityByPolicyId(DeleteSupplierPriorityCommand command){
		
		// 查询优先级策略
		SupplierPriorityPolicy supplierPriorityPolicy = supplierPriorityPolicyService.get(command.getSupplierPriorityPolicyId());
		
		// 查询该策略中的所有供应商优先级并删除
		SupplierPriorityQO supplierPriorityQo = new SupplierPriorityQO();
		supplierPriorityQo.setSupplierPriorityPolicyId(command.getSupplierPriorityPolicyId());
		List<SupplierPriority> supplierPriorityList = this.queryList(supplierPriorityQo);
		
		for (SupplierPriority supplierPriority : supplierPriorityList) {
			
			//获取当前供应商优先级中项目和供应商信息
			Project project=projectService.get(supplierPriority.getProject().getId());
			Supplier supplier=supplierService.get(supplierPriority.getSupplier().getId());
			
			this.deleteById(supplierPriority.getId());
			logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除"+project.getName()+"项目，供应商" + supplier.getBaseInfo().getName()+"优先级", command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
		}
		
		// 删除该优先级策略
		supplierPriorityPolicyService.deleteById(command.getSupplierPriorityPolicyId());
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除"+supplierPriorityPolicy.getProjectName()+"项目，供应商优先级策略 ", command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	
	/**
	 * 根据供应商id列表，删除供应商优先级及修改策略
	 * @param command
	 */
	public void deleteSupplierPriorityBySupplierIds(DeleteSupplierPriorityCommand command){
		
		//根据供应商id，查询供应商优先级
		SupplierPriorityQO supplierPriorityQo = new SupplierPriorityQO();
		List<String>supplierIds=command.getSupplierIds();
		
		for (String supplierId : supplierIds) {
			
			supplierPriorityQo.setSupplierId(supplierId);
			//根据供应商id，获取此供应商所有供应商优先级信息
			List<SupplierPriority> supplierPriorityList=supplierPriorityDao.queryList(supplierPriorityQo);
			
			for (SupplierPriority supplierPriority : supplierPriorityList) {
				//获取包含原供应商名称的供应商优先级策略
				SupplierPriorityPolicy policy=supplierPriorityPolicyService.get(supplierPriority.getPolicy().getId());
				
				//获取当前供应商优先级中项目和供应商信息
				Project project=projectService.get(supplierPriority.getProject().getId());
				Supplier supplier=supplierService.get(supplierPriority.getSupplier().getId());
				
				this.deleteById(supplierPriority.getId());
				logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除"+project.getName()+"项目，供应商" + supplier.getBaseInfo().getName()+"优先级", command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
				
				//更新该供应商优先级策略
				ModifySupplierPriorityPolicyCommand modifySupplierPriorityPolicyCommand=new ModifySupplierPriorityPolicyCommand();
				modifySupplierPriorityPolicyCommand.setPolicyId(supplierPriority.getPolicy().getId());
				modifySupplierPriorityPolicyCommand.setSupplierName(policy.getSupplierName().replaceAll(supplier.getBaseInfo().getName()+" ", ""));
				
				supplierPriorityPolicyService.updateSupplierPriorityPolicy(modifySupplierPriorityPolicyCommand);
				
				logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改"+policy.getProjectName()+"项目供应商优先级策略为"+ modifySupplierPriorityPolicyCommand.getSupplierName(),	command.getOperatorName(), command.getOperatorType(),command.getOperatorAccount(), "");
			}
		}

		
	}

	/**
	 * 查找没有设置供应商优先级的项目
	 * 
	 * @return
	 */
	public List<Project> findProjectNotInSupplierPriority() {
		
		//查询项目列表
		ProjectQO projectQo = new ProjectQO();
		List<Project> projectList = projectService.queryList(projectQo);
		
		//查询供应商优先级列表
		SupplierPriorityQO supplierPriorityQO = new SupplierPriorityQO();
		List<SupplierPriority> supplierPrioritylist = queryList(supplierPriorityQO);
		
		//把已添加供应商优先级的项目添加到HashSet中
		Set<Project> projects = new HashSet<Project>();
		for (SupplierPriority supplierPriority : supplierPrioritylist) {
			projects.add(supplierPriority.getProject());
		}
		//把已添加供应商优先级的项目从项目列表中删除
		projectList.removeAll(projects);
		
		return projectList;
	}

}
