package zzpl.app.service.local.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.user.RoleDAO;
import zzpl.app.dao.user.RoleWorkflowDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.app.dao.workflow.WorkflowDAO;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.app.dao.workflow.WorkflowStepDAO;
import zzpl.domain.model.user.Role;
import zzpl.domain.model.user.RoleWorkflow;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.StepAction;
import zzpl.domain.model.workflow.Workflow;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.command.workflow.AddWorkflowCommand;
import zzpl.pojo.command.workflow.ConfigWorkflowCommand;
import zzpl.pojo.command.workflow.DeleteWorkflowCommand;
import zzpl.pojo.command.workflow.ModifyWorkflowCommand;
import zzpl.pojo.dto.workflow.ExecutorDTO;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.user.RoleWorkflowQO;
import zzpl.pojo.qo.workflow.WorkflowQO;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;
import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;

@Service
@Transactional
public class WorkflowService extends BaseServiceImpl<Workflow, WorkflowQO, WorkflowDAO> {

	@Autowired
	private WorkflowDAO workflowDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleWorkflowDAO roleWorkflowDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private WorkflowStepDAO workflowStepDAO;
	@Autowired
	private WorkflowStepActionDAO workflowStepActionDAO;
	@Autowired
	private StepActionDAO stepActionDAO;
	
	/**
	 * @Title: addWorkflow 
	 * @author guok
	 * @Description: 添加流程
	 * @time 2015年7月8日 15:31:05
	 * @param command
	 * @throws WorkflowException void 设定文件
	 * @throws
	 */
	public void addWorkflow(AddWorkflowCommand command) throws WorkflowException {
		if (StringUtils.isBlank(command.getUserID())) {
			throw new WorkflowException(WorkflowException.USER_ID_NULL_CODE,WorkflowException.USER_ID_NULL_MESSAGE);
		}
		User user = userDAO.get(command.getUserID());
		if (user==null) {
			throw new WorkflowException(WorkflowException.USER_ID_NULL_CODE,WorkflowException.USER_ID_NULL_MESSAGE);
		}
		Workflow workflow = new Workflow();
		workflow.setId(UUIDGenerator.getUUID());
		workflow.setWorkflowName(command.getWorkflowName());
		workflow.setCompanyID(user.getCompanyID());
		workflow.setCompanyName(user.getCompanyName());
		workflow.setCreateTime(new Date());
		workflowDAO.save(workflow);
		//创建角色流程关联表
		if (command.getRoleIds() != null && command.getRoleIds().length > 0) {
			for (String roleID : command.getRoleIds()) {
				Role role = roleDAO.get(roleID);
				if (role!=null) {
					RoleWorkflow roleWorkflow = new RoleWorkflow();
					roleWorkflow.setId(UUIDGenerator.getUUID());
					roleWorkflow.setRole(role);
					roleWorkflow.setWorkflow(workflow);
					roleWorkflowDAO.save(roleWorkflow);
				}
			}
		}
	}
	
	/**
	 * @Title: modifyWorkflow 
	 * @author guok
	 * @Description: 修改流程
	 * @Time 2015年7月8日 16:04:47
	 * @param command void 设定文件
	 * @throws
	 */
	public void modifyWorkflow(ModifyWorkflowCommand command) {
		Workflow workflow = workflowDAO.get(command.getWorkflowID());
		workflow.setWorkflowName(command.getWorkflowName());
		//删除原有中间表
		RoleWorkflowQO roleWorkflowQO = new RoleWorkflowQO();
		roleWorkflowQO.setWorkflowID(workflow.getId());
		List<RoleWorkflow> RoleWorkflows = roleWorkflowDAO.queryList(roleWorkflowQO);
		if (RoleWorkflows.size()>0) {
			for (RoleWorkflow roleWorkflow : RoleWorkflows) {
				roleWorkflowDAO.delete(roleWorkflow);
			}
		}
		
		//重新创建中间关联表
		if (command.getRoleIds() != null && command.getRoleIds().length > 0) {
			for (String roleID : command.getRoleIds()) {
				Role role = roleDAO.get(roleID);
				if (role!=null) {
					RoleWorkflow roleWorkflow = new RoleWorkflow();
					roleWorkflow.setId(UUIDGenerator.getUUID());
					roleWorkflow.setRole(role);
					roleWorkflow.setWorkflow(workflow);
					roleWorkflowDAO.save(roleWorkflow);
				}
			}
		}
	}
	
	/**
	 * @Title: deleteWorkflow 
	 * @author guok
	 * @Description: 删除，暂时不做操作，后期监控流程运行后在进行操作
	 * @param command void 设定文件
	 * @throws
	 */
	public void deleteWorkflow(DeleteWorkflowCommand command) {
		/*//先删除中间表
		RoleWorkflowQO roleWorkflowQO = new RoleWorkflowQO();
		roleWorkflowQO.setWorkflowID(command.getWorkflowID());
		List<RoleWorkflow> roleWorkflows = roleWorkflowDAO.queryList(roleWorkflowQO);
		if (roleWorkflows.size()>0) {
			for (RoleWorkflow roleWorkflow : roleWorkflows) {
				roleWorkflowDAO.delete(roleWorkflow);
			}
		}
		
		Workflow workflow = workflowDAO.get(command.getWorkflowID());
		workflowDAO.delete(workflow);*/
	}
	
	/**
	 * @Title: getRoleWorkflowByQo 
	 * @author guok
	 * @Description: 根据ID查询流程
	 * @param qo
	 * @return Workflow 设定文件
	 * @throws
	 */
	public Workflow getWorkflowByQo(WorkflowQO qo) {
		Workflow workflow = workflowDAO.queryUnique(qo);
		Hibernate.initialize(workflow.getRoleWorkflows());
		return workflow;
	}

	/**
	 * 
	 * @方法功能说明：配置流程环节
	 * @修改者名字：cangs
	 * @修改时间：2015年8月12日下午3:42:40
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void config(ConfigWorkflowCommand command) {
		Workflow workflow = workflowDAO.get(command.getWorkflowID());
		WorkflowStep workflowStep = new WorkflowStep();
		//主键
		workflowStep.setId(UUIDGenerator.getUUID());
		//公司ID
		workflowStep.setCompanyID(workflow.getCompanyID());
		//公司名
		workflowStep.setCompanyName(workflow.getCompanyName());
		//下一步环节编号，默认为办结
		workflowStep.setNextStepNO("99;");
		//环节名称
		workflowStep.setStepName(command.getStepName());
		WorkflowStepQO workflowStepQO = new WorkflowStepQO();
		workflowStepQO.setWorkflowID(command.getWorkflowID());
		Integer workflowStepSum = workflowStepDAO.queryCount(workflowStepQO);
		//如果该流程目前没有任何环节，说明是起草环节
		if(workflowStepSum==0){
			//上一环节编号0
			workflowStep.setPreviousStepNO("0");
			//环节编号1
			workflowStep.setStepNO(1);
		}else{
			workflowStep.setStepNO(workflowStepDAO.maxProperty("stepNO", workflowStepQO)+1);
			switch (command.getChooseExecutorType()) {
			case 2:
				//执行人类型
				workflowStep.setChooseExecutorType(command.getChooseExecutorType());
				//执行人
				workflowStep.setExecutor(command.getExecutor());
				break;
			case 4:
				//执行人类型
				workflowStep.setChooseExecutorType(command.getChooseExecutorType());
				//执行人
				ExecutorDTO executorDTO = new ExecutorDTO();
				List<Integer> userFlag = new ArrayList<Integer>();
				userFlag.add(Integer.parseInt(command.getExecutor()));
				executorDTO.setUserFlag(userFlag);
				workflowStep.setExecutor(JSON.toJSONString(executorDTO));
				break;
			}
		}
		//环节类型 1:送出 2:退回
		workflowStep.setStepType(1);
		//流程ID
		workflowStep.setWorkflowID(command.getWorkflowID());
		//流程名称
		workflowStep.setWorkflowName(workflow.getWorkflowName());
		workflowStepDAO.save(workflowStep);
		if(StringUtils.isNotBlank(command.getActionID())){
			String[] actionIDs = command.getActionID().split(",");
			for (int i = 0; i < actionIDs.length; i++) {
				WorkflowStepAction workflowStepAction = new WorkflowStepAction();
				workflowStepAction.setId(UUIDGenerator.getUUID());
				StepAction stepAction = stepActionDAO.get(actionIDs[i]); 
				workflowStepAction.setStepAction(stepAction);
				workflowStepAction.setOrderType(stepAction.getOrderType());
				workflowStepAction.setWorkflowStep(workflowStep);
				workflowStepAction.setStatus(1);
				workflowStepAction.setSort(workflowStepActionDAO.maxProperty("sort", new WorkflowStepActionQO())+1);
				workflowStepActionDAO.save(workflowStepAction);
			}
		}
	}
	
	@Override
	protected WorkflowDAO getDao() {
		return workflowDAO;
	}

}
