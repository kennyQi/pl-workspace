package jxc.app.service.system;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateProjectCommand;
import hg.pojo.command.DeleteProjectCommand;
import hg.pojo.command.ModifyProjectCommand;
import hg.pojo.command.ModifySupplierPriorityPolicyCommand;
import hg.pojo.exception.ProjectException;
import hg.pojo.qo.DealerProductMappingQO;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.ProjectQO;
import hg.pojo.qo.SupplierPriorityPolicyQO;
import hg.pojo.qo.SupplierPriorityQO;

import java.util.List;

import jxc.app.dao.system.ProjectDao;
import jxc.app.service.product.DealerProductMappingService;
import jxc.app.service.supplier.SupplierPriorityPolicyService;
import jxc.app.service.supplier.SupplierPriorityService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.DealerProductMapping;
import jxc.domain.model.product.Product;
import jxc.domain.model.supplier.SupplierPriority;
import jxc.domain.model.supplier.SupplierPriorityPolicy;
import jxc.domain.model.system.OperationForm;
import jxc.domain.model.system.Project;
import jxc.domain.model.system.ProjectMode;
import jxc.domain.model.system.ProjectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
@Service
public class ProjectService extends BaseServiceImpl<Project,ProjectQO,ProjectDao>{
	
	@Autowired
	private DealerProductMappingService dealerProductMappingService;
	
	@Autowired
	private SupplierPriorityService supplierPriorityService;
	
	@Autowired
	private JxcLogger logger;
	
	@Autowired
	private ProjectTypeService projectTypeService;
	
	@Autowired
	private ProjectModeService projectModeService;
	
	@Autowired
	private OperationFormService operationFormService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private SupplierPriorityPolicyService supplierPriorityPolicyService;
	
	
	/**
	 * 新建项目
	 * @param command
	 * @throws ProjectException
	 */
	public void createProjcet(CreateProjectCommand command) throws ProjectException{
		
		//判断项目名称是否重复
		if(projectNameIsExisted(command.getName(),null, true)){
			
			//项目名称已存在
			throw new ProjectException(ProjectException.RESLUT_PROJECT_NAME_REPEAT, "项目名称已存在");
		}
		
		//项目名称不存在，创建新项目
		ProjectType type=projectTypeService.get(command.getProjectTypeId());
		ProjectMode model=projectModeService.get(command.getProjectModeId());
		OperationForm form=operationFormService.get(command.getOperationFormId());
		
		Project project = new Project();
		project.createProject(command,type,model,form);
		this.save(project);
		
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"新增项目 " +JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	/**
	 * 更新项目
	 * @param command
	 * @throws ProjectException
	 */
	public void updateProjcet(ModifyProjectCommand command) throws ProjectException{
		
		//判断项目名称是否重复
		if(projectNameIsExisted(command.getName(),command.getProjectId(), false)){
			
			//项目名称已存在
			throw new ProjectException(ProjectException.RESLUT_PROJECT_NAME_REPEAT, "项目名称已存在");
		}
		
		//项目名称不重复，可以修改
		ProjectType type=projectTypeService.get(command.getProjectTypeId());
		ProjectMode model=projectModeService.get(command.getProjectModeId());
		OperationForm form=operationFormService.get(command.getOperationFormId());
		
		//获取原项目
		Project project =this.get(command.getProjectId());
		String oldProjectName=project.getName();
		
		//查询包含原项目名称的供应商优先级策略
		SupplierPriorityPolicyQO supplierPriorityPolicyQO=new SupplierPriorityPolicyQO();
		supplierPriorityPolicyQO.setProjectName(oldProjectName);
		SupplierPriorityPolicy policy=supplierPriorityPolicyService.queryUnique(supplierPriorityPolicyQO);
		
		//更新项目
		project.modifyProject(command,type,model,form);
		this.update(project);
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改项目" +JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
		

		if(policy!=null && (!oldProjectName.equals(project.getName()))){
			//更新供应商策略
			ModifySupplierPriorityPolicyCommand supplierPriorityPolicyCommand=new ModifySupplierPriorityPolicyCommand();
			supplierPriorityPolicyCommand.setPolicyId(policy.getId());
			supplierPriorityPolicyCommand.setProjectName(policy.getProjectName().replaceAll(oldProjectName, policy.getProjectName()));

			supplierPriorityPolicyService.updateSupplierPriorityPolicy(supplierPriorityPolicyCommand);
			
			logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改"+policy.getProjectName()+"项目供应商优先级策略为"+ supplierPriorityPolicyCommand.getProjectName(),	command.getOperatorName(), command.getOperatorType(),command.getOperatorAccount(), "");
		}
	}

	/**
	 * 删除项目
	 * @param command
	 * @throws ProjectException
	 */
	public void deleteProjcet(DeleteProjectCommand command) throws ProjectException{
		
		if(checkProjectIsUse(command.getProjectId())){
			
			//项目已使用，不能删除
			throw new ProjectException(ProjectException.RESULT_PROJECT_USE, "项目已使用，不能删除");
		}
		
		//项目未被使用，删除项目
		Project project=this.get(command.getProjectId());
		project.setStatusRemoved(true);
		
		update(project);

		logger.debug(this.getClass(), "czh", command.getOperatorName()+"删除项目 " + project.getName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	
	/**
	 * 判断该项目是否被使用
	 * @param project
	 * @return
	 */
	public boolean checkProjectIsUse(String projectId){
		
		DealerProductMappingQO qo = new DealerProductMappingQO();
		qo.setProjectId(projectId);
		List<DealerProductMapping> list = dealerProductMappingService.queryList(qo);
		
		SupplierPriorityQO supplierPriorityQO = new SupplierPriorityQO();
		supplierPriorityQO.setProjectId(projectId);
		List<SupplierPriority> supplierPriorities = supplierPriorityService.queryList(supplierPriorityQO);
		
		if(list.size() > 0 || supplierPriorities.size() > 0){
			//true表示已使用
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断项目名称是否存在
	 * @param projectName
	 * @param isCreate
	 * @return
	 */
	private boolean projectNameIsExisted(String name, String id, boolean isCreate) {
		ProjectQO qo = new ProjectQO();
		qo.setName(name);
		Project project = queryUnique(qo);

		if (isCreate) {
			if (project != null) {
				return true;
			}
		} else {
			if (project!=null && !id.equals(project.getId())) {
				return true;
			}
		}
		return false;

	}
	
	
	
	@Override
	protected ProjectDao getDao() {
		return projectDao;
	}

}
